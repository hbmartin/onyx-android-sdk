#![allow(non_snake_case)]

use std::collections::HashMap;
use std::sync::atomic::{AtomicI32, AtomicI64, Ordering};
use std::sync::{Mutex, OnceLock};

use jni::objects::{JDoubleArray, JFloatArray, JIntArray, JObject, JObjectArray, JValue};
use jni::sys::{jboolean, jint, jlong, jobject, jobjectArray};
use jni::JNIEnv;

#[derive(Clone, Copy, Debug, Default, PartialEq)]
struct Touch {
    x: f32,
    y: f32,
    pressure: f32,
    size: f32,
    tilt_x: f32,
    tilt_y: f32,
    timestamp: f64,
}

#[allow(dead_code)]
#[derive(Clone, Debug)]
struct PenConfig {
    color: i32,
    width: f32,
    min_width: f32,
    dpi: f32,
    display_scale_x: f32,
    display_scale_y: f32,
    scale_precision: f32,
    rotate_angle: i32,
    brush_shape: i32,
    brush_spacing: f32,
    brush_ratio: f32,
    brush_angle: f32,
    pressure_sensitivity: f32,
    velocity_sensitivity: f32,
    velocity_amplifier: f32,
    velocity_ignore_threshold: f32,
    velocity_lower_bound: f32,
    velocity_upper_bound: f32,
    smooth_level: f32,
    tilt_enabled: bool,
    tilt_scale: f32,
    direction_enabled: bool,
    fast_mode: bool,
}

impl Default for PenConfig {
    fn default() -> Self {
        Self {
            color: 0xff00_0000u32 as i32,
            width: 3.0,
            min_width: 0.001,
            dpi: 320.0,
            display_scale_x: 1.0,
            display_scale_y: 1.0,
            scale_precision: 1.0,
            rotate_angle: 0,
            brush_shape: 0,
            brush_spacing: 0.25,
            brush_ratio: 5.0,
            brush_angle: 0.0,
            pressure_sensitivity: 0.3,
            velocity_sensitivity: 0.5,
            velocity_amplifier: 0.0,
            velocity_ignore_threshold: 0.0,
            velocity_lower_bound: 0.0,
            velocity_upper_bound: 0.0,
            smooth_level: 0.2,
            tilt_enabled: false,
            tilt_scale: 3.0,
            direction_enabled: false,
            fast_mode: false,
        }
    }
}

#[derive(Clone, Debug)]
struct Stamp {
    width: i32,
    height: i32,
    alpha: u8,
}

#[derive(Clone, Debug, Default)]
struct InkData {
    points: Vec<f32>,
    point_sizes: Vec<i32>,
    stamps: Vec<Stamp>,
}

#[derive(Clone, Debug)]
struct PenState {
    pen_type: i32,
    config: PenConfig,
    last: Option<Touch>,
    last_width: f32,
    history: Vec<Touch>,
}

#[derive(Clone, Copy, Debug, Eq, PartialEq)]
enum Phase {
    Down,
    Move,
    Up,
}

impl PenState {
    fn new(pen_type: i32, config: PenConfig) -> Self {
        let last_width = config.width.max(config.min_width);
        Self {
            pen_type,
            config,
            last: None,
            last_width,
            history: Vec::new(),
        }
    }

    fn reset(&mut self) {
        self.last = None;
        self.last_width = self.config.width.max(self.config.min_width);
        self.history.clear();
    }

    fn transform(&self, mut point: Touch) -> Touch {
        point.x *= self.config.display_scale_x;
        point.y *= self.config.display_scale_y;
        point.pressure = point.pressure.clamp(0.0, 1.0);
        point
    }

    fn brush_width(&self, point: Touch) -> f32 {
        (self.config.width * 2.0 * point.pressure.max(0.0).sqrt()).clamp(
            self.config.min_width.max(0.001),
            self.config.width * 2.0 + 3.0,
        )
    }

    fn fountain_width(&self, point: Touch) -> f32 {
        2.0 + point.pressure.clamp(0.0, 1.0).powi(2) * (self.config.width + 1.0)
    }

    fn marker_width(&self, point: Touch) -> f32 {
        let factor = if point.pressure <= 0.3 {
            0.8
        } else if point.pressure <= 0.6 {
            0.9
        } else {
            1.0
        };
        self.config.width * factor
    }

    #[cfg(test)]
    fn width_for(&self, previous: Option<Touch>, point: Touch) -> f32 {
        let sensitivity = self.config.pressure_sensitivity.clamp(0.0, 1.0);
        let pressure = point.pressure.max(0.001).powf(1.35 - sensitivity);
        let pressure_width = self.config.width * (0.12 + (0.88 * pressure));
        let velocity = previous
            .map(|last| {
                let distance = ((point.x - last.x).powi(2) + (point.y - last.y).powi(2)).sqrt();
                let millis = ((point.timestamp - last.timestamp).abs() as f32).max(1.0);
                distance / millis
            })
            .unwrap_or(0.0);
        let velocity_factor = 1.0
            - (velocity * self.config.velocity_sensitivity.clamp(0.0, 1.0) * 0.35).clamp(0.0, 0.7)
            + self.config.velocity_amplifier.clamp(0.0, 1.0) * 0.1;
        let tilt_factor = if self.config.tilt_enabled {
            let tilt = (point.tilt_x.abs() + point.tilt_y.abs()).min(180.0) / 180.0;
            1.0 + (tilt * self.config.tilt_scale.max(0.0) * 0.05)
        } else {
            1.0
        };
        (pressure_width * velocity_factor * tilt_factor).clamp(
            self.config.min_width.max(0.001),
            self.config.width.max(self.config.min_width),
        )
    }

    fn push_point(ink: &mut InkData, point: Touch, width: f32) {
        ink.points.extend_from_slice(&[point.x, point.y, width]);
        ink.point_sizes.push(3);
    }

    fn interpolate_linear(start: Touch, end: Touch, steps: usize) -> Vec<Touch> {
        (1..=steps)
            .map(|index| {
                let t = index as f32 / steps as f32;
                Touch {
                    x: start.x + (end.x - start.x) * t,
                    y: start.y + (end.y - start.y) * t,
                    pressure: start.pressure + (end.pressure - start.pressure) * t,
                    size: start.size + (end.size - start.size) * t,
                    tilt_x: start.tilt_x + (end.tilt_x - start.tilt_x) * t,
                    tilt_y: start.tilt_y + (end.tilt_y - start.tilt_y) * t,
                    timestamp: start.timestamp + (end.timestamp - start.timestamp) * t as f64,
                }
            })
            .collect()
    }

    fn encode_paths(&self, samples: &[(Touch, f32)], ink: &mut InkData) {
        let Some((start, start_width)) = samples.first().copied() else {
            return;
        };
        for (point, width) in samples.iter().skip(1) {
            let start_half = (start_width * 0.5).max(0.5);
            let half = (*width * 0.5).max(0.5);
            ink.points.extend_from_slice(&[
                start.x - start_half,
                start.y,
                start.x,
                start.y - start_half,
                point.x,
                point.y - half,
                point.x + half,
                point.y,
                point.x,
                point.y + half,
                start.x,
                start.y + start_half,
            ]);
            ink.point_sizes.push(12);
        }
    }

    fn encode_axis_paths(
        start: Touch,
        endpoints: &[Touch],
        width: f32,
        first_segment_is_partial: bool,
    ) -> InkData {
        let mut ink = InkData::default();
        let half = (width * 0.5).max(0.5);
        for (index, point) in endpoints.iter().enumerate() {
            if index == 0 && first_segment_is_partial {
                ink.points.extend_from_slice(&[
                    start.x - half,
                    start.y,
                    start.x,
                    start.y - half,
                    point.x,
                    point.y - half,
                    point.x + half,
                    point.y,
                    point.x,
                    point.y + half,
                    start.x,
                    start.y + half,
                ]);
            } else {
                ink.points.extend_from_slice(&[
                    start.x - half,
                    start.y,
                    start.x,
                    start.y - half,
                    start.x + half,
                    start.y,
                    point.x + half,
                    point.y,
                    point.x,
                    point.y + half,
                    point.x - half,
                    point.y,
                ]);
            }
            ink.point_sizes.push(12);
        }
        ink
    }

    fn normalized_direction(start: Touch, end: Touch) -> (f32, f32) {
        let dx = end.x - start.x;
        let dy = end.y - start.y;
        let length = (dx * dx + dy * dy).sqrt();
        if length <= f32::EPSILON {
            (0.0, 0.0)
        } else {
            (dx / length, dy / length)
        }
    }

    fn vector_prediction(&self, prediction: Touch, fountain: bool) -> InkData {
        let Some(start) = self.history.first().copied() else {
            return InkData::default();
        };
        if !fountain {
            let (prediction_dx, prediction_dy) = Self::normalized_direction(
                self.history.last().copied().unwrap_or(start),
                prediction,
            );
            let mut endpoints = Vec::new();
            if let Some(first) = self.history.get(1).copied() {
                let (first_dx, first_dy) = Self::normalized_direction(start, first);
                endpoints.push(Touch {
                    x: first.x + first_dx * 0.1,
                    y: first.y + first_dy * 0.1,
                    ..first
                });
            }
            endpoints.push(Touch {
                x: prediction.x + prediction_dx * 0.69,
                y: prediction.y + prediction_dy * 0.69,
                ..prediction
            });
            return Self::encode_axis_paths(start, &endpoints, self.config.width, true);
        }

        let mut endpoints = Vec::new();
        if self.history.len() >= 2 {
            let first = self.history[1];
            endpoints.push(Touch {
                x: start.x + (first.x - start.x) * 0.035,
                y: start.y + (first.y - start.y) * 0.035,
                pressure: start.pressure + (first.pressure - start.pressure) * 0.035,
                ..first
            });
        }
        let last = self.history.last().copied().unwrap_or(start);
        endpoints.push(Touch {
            x: last.x + (prediction.x - last.x) * 0.86,
            y: last.y + (prediction.y - last.y) * 0.86,
            pressure: last.pressure + (prediction.pressure - last.pressure) * 0.86,
            ..prediction
        });
        if fountain && endpoints.len() == 2 {
            let middle = endpoints[1];
            endpoints.insert(
                1,
                Touch {
                    x: (endpoints[0].x + middle.x) * 0.5,
                    y: (endpoints[0].y + middle.y) * 0.5,
                    pressure: (endpoints[0].pressure + middle.pressure) * 0.5,
                    ..middle
                },
            );
        }
        let base_width = if fountain {
            self.config.width * 0.6
        } else {
            self.config.width
        };
        let mut samples = vec![(start, base_width)];
        samples.extend(endpoints.into_iter().map(|point| (point, base_width)));
        let mut ink = InkData::default();
        self.encode_paths(&samples, &mut ink);
        ink
    }

    fn vector_finish(&self, end: Touch, fountain: bool) -> InkData {
        let Some(start) = self.history.first().copied() else {
            return InkData::default();
        };
        let last = self.history.last().copied().unwrap_or(start);
        let endpoint = Touch {
            x: last.x + (end.x - last.x) * 0.73,
            y: last.y + (end.y - last.y) * 0.73,
            pressure: last.pressure + (end.pressure - last.pressure) * 0.73,
            ..end
        };
        if !fountain {
            return Self::encode_axis_paths(start, &[endpoint], self.config.width, false);
        }
        let width = if fountain {
            self.config.width * 0.6
        } else {
            self.config.width
        };
        let mut samples = vec![(start, width)];
        if fountain && self.history.len() > 2 {
            let middle = self.history[self.history.len() / 2];
            samples.push((middle, width));
        }
        samples.push((endpoint, width));
        let mut ink = InkData::default();
        self.encode_paths(&samples, &mut ink);
        ink
    }

    fn pencil_ink(&self, points: &[Touch], sample_count: usize) -> InkData {
        if points.len() < 2 {
            return InkData::default();
        }
        let mut distances = Vec::with_capacity(points.len());
        distances.push(0.0);
        for pair in points.windows(2) {
            let distance =
                ((pair[1].x - pair[0].x).powi(2) + (pair[1].y - pair[0].y).powi(2)).sqrt();
            distances.push(distances.last().copied().unwrap_or(0.0) + distance);
        }
        let total = *distances.last().unwrap_or(&0.0);
        let mut ink = InkData::default();
        for index in 0..sample_count {
            let target = total * index as f32 / (sample_count.saturating_sub(1).max(1)) as f32;
            let segment = distances
                .windows(2)
                .position(|range| target <= range[1])
                .unwrap_or(points.len() - 2);
            let span = (distances[segment + 1] - distances[segment]).max(f32::EPSILON);
            let t = (target - distances[segment]) / span;
            let a = points[segment];
            let b = points[segment + 1];
            let point = Self::interpolate_linear(a, b, 100)[(t * 99.0).round() as usize];
            let angle = (b.y - a.y)
                .atan2(b.x - a.x)
                .rem_euclid(std::f32::consts::TAU);
            let width =
                (0.5 + self.config.width * point.pressure.sqrt()).max(self.config.min_width);
            let alpha = (0.05 + 0.4 * point.pressure).clamp(0.0, 1.0);
            ink.points
                .extend_from_slice(&[point.x, point.y, width, angle, alpha]);
            ink.point_sizes.push(5);
        }
        ink
    }

    fn process(
        &mut self,
        input: &[Touch],
        prediction: Option<Touch>,
        phase: Phase,
    ) -> (InkData, InkData) {
        let transformed = input
            .iter()
            .map(|point| self.transform(*point))
            .collect::<Vec<_>>();
        let mut real = InkData::default();
        let mut predicted = InkData::default();
        match phase {
            Phase::Down => {
                self.reset();
                let Some(point) = transformed.first().copied() else {
                    return (real, predicted);
                };
                self.history.push(point);
                self.last = Some(point);
                match self.pen_type {
                    1 => {
                        self.last_width = self.brush_width(point);
                        Self::push_point(&mut real, point, self.last_width);
                    }
                    2 => Self::push_point(&mut real, point, self.fountain_width(point)),
                    3 => Self::push_point(&mut real, point, self.marker_width(point)),
                    _ => {}
                }
            }
            Phase::Move => {
                let previous_history = self.history.clone();
                self.history.extend(transformed.iter().copied());
                self.last = self.history.last().copied();
                match self.pen_type {
                    1 => {
                        if let Some(point) = previous_history.last().copied() {
                            // The reference brush delays committed coordinates by one input
                            // batch and applies its tiny first-stage width relaxation to the
                            // delayed point.  The final up point still uses the direct pressure
                            // curve below.
                            self.last_width *= 1.00375;
                            Self::push_point(&mut real, point, self.last_width);
                        }
                    }
                    2 => {
                        let mut previous = previous_history.last().copied();
                        for point in transformed {
                            if let Some(start) = previous {
                                let distance = ((point.x - start.x).powi(2)
                                    + (point.y - start.y).powi(2))
                                .sqrt();
                                let steps = (distance / 4.0).ceil().max(1.0) as usize;
                                let start_width = self.fountain_width(start);
                                let end_width = self.fountain_width(point);
                                for (index, sample) in Self::interpolate_linear(start, point, steps)
                                    .into_iter()
                                    .enumerate()
                                {
                                    let t = (index + 1) as f32 / steps as f32;
                                    let width = start_width + (end_width - start_width) * t;
                                    Self::push_point(&mut real, sample, width);
                                }
                            } else {
                                Self::push_point(&mut real, point, self.fountain_width(point));
                            }
                            previous = Some(point);
                        }
                    }
                    3 => {
                        for point in transformed {
                            Self::push_point(&mut real, point, self.marker_width(point));
                        }
                    }
                    4 => {
                        if let Some(point) = previous_history.first().copied() {
                            real.points
                                .extend_from_slice(&[point.x - 3.0, point.y - 3.0]);
                            real.point_sizes.push(2);
                            real.stamps.push(Stamp {
                                width: 13,
                                height: 14,
                                alpha: 180,
                            });
                        }
                    }
                    6 if self.config.fast_mode => {
                        if previous_history.len() == 1 && !transformed.is_empty() {
                            let point =
                                Self::interpolate_linear(previous_history[0], transformed[0], 100)
                                    [17];
                            Self::push_point(&mut real, point, 0.5 + 2.5 * point.pressure);
                        }
                    }
                    6 | 8 | 9 => {
                        if let Some(point) = prediction.map(|point| self.transform(point)) {
                            predicted = self.vector_prediction(point, self.pen_type == 6);
                        }
                    }
                    7 => {
                        if let Some(point) = prediction.map(|point| self.transform(point)) {
                            let mut points = self.history.clone();
                            points.push(point);
                            predicted = self.pencil_ink(&points, 33);
                        }
                    }
                    _ => {}
                }
            }
            Phase::Up => {
                let Some(end) = transformed.first().copied() else {
                    return (real, predicted);
                };
                match self.pen_type {
                    1 => {
                        if let Some(point) = self.history.last().copied() {
                            Self::push_point(&mut real, point, self.last_width);
                        }
                        Self::push_point(&mut real, end, self.brush_width(end));
                    }
                    2 => {
                        let start = self.history.last().copied().unwrap_or(end);
                        let distance =
                            ((end.x - start.x).powi(2) + (end.y - start.y).powi(2)).sqrt();
                        let steps = (distance / 3.0).ceil().max(1.0) as usize;
                        let start_width = self.fountain_width(start);
                        let end_width = self.fountain_width(end);
                        for (index, point) in Self::interpolate_linear(start, end, steps)
                            .into_iter()
                            .enumerate()
                        {
                            let t = (index + 1) as f32 / steps as f32;
                            let width = start_width + (end_width - start_width) * t;
                            Self::push_point(&mut real, point, width);
                        }
                    }
                    3 => Self::push_point(&mut real, end, self.marker_width(end)),
                    4 => {
                        let point = self.history.last().copied().unwrap_or(end);
                        real.points
                            .extend_from_slice(&[point.x - 4.0, point.y - 4.0]);
                        real.point_sizes.push(2);
                        real.stamps.push(Stamp {
                            width: 13,
                            height: 13,
                            alpha: 180,
                        });
                    }
                    5 => {
                        let point = self.history.first().copied().unwrap_or(end);
                        real.points
                            .extend_from_slice(&[point.x - 4.0, point.y - 4.0]);
                        real.point_sizes.push(2);
                        real.stamps.push(Stamp {
                            width: 18,
                            height: 19,
                            alpha: 180,
                        });
                    }
                    6 if self.config.fast_mode => {
                        let mut points = self.history.clone();
                        points.push(end);
                        for pair in [(0.22, 0), (0.86, 1), (0.73, points.len().saturating_sub(2))] {
                            let index = pair.1.min(points.len().saturating_sub(2));
                            let point =
                                Self::interpolate_linear(points[index], points[index + 1], 100)
                                    [(pair.0 * 99.0) as usize];
                            Self::push_point(&mut real, point, 0.5 + 2.5 * point.pressure);
                        }
                    }
                    6 | 8 | 9 => real = self.vector_finish(end, self.pen_type == 6),
                    7 => {
                        let mut points = self.history.clone();
                        points.push(end);
                        real = self.pencil_ink(&points, 31);
                    }
                    _ => {}
                }
                self.history.push(end);
                self.last = Some(end);
            }
        }
        (real, predicted)
    }
}

struct Runtime {
    next_handle: AtomicI64,
    pens: Mutex<HashMap<i64, PenState>>,
    log_level: AtomicI32,
    legacy_handle: Mutex<Option<i64>>,
    legacy_bitmaps: Mutex<Vec<Stamp>>,
}

fn runtime() -> &'static Runtime {
    static RUNTIME: OnceLock<Runtime> = OnceLock::new();
    RUNTIME.get_or_init(|| Runtime {
        next_handle: AtomicI64::new(1),
        pens: Mutex::new(HashMap::new()),
        log_level: AtomicI32::new(0),
        legacy_handle: Mutex::new(None),
        legacy_bitmaps: Mutex::new(Vec::new()),
    })
}

fn get_float(env: &mut JNIEnv, object: &JObject, name: &str, default: f32) -> f32 {
    env.get_field(object, name, "F")
        .and_then(|value| value.f())
        .unwrap_or(default)
}

fn get_int(env: &mut JNIEnv, object: &JObject, name: &str, default: i32) -> i32 {
    env.get_field(object, name, "I")
        .and_then(|value| value.i())
        .unwrap_or(default)
}

fn get_bool(env: &mut JNIEnv, object: &JObject, name: &str, default: bool) -> bool {
    env.get_field(object, name, "Z")
        .and_then(|value| value.z())
        .unwrap_or(default)
}

fn read_config(env: &mut JNIEnv, object: &JObject) -> PenConfig {
    let defaults = PenConfig::default();
    let lower_bound = get_float(
        env,
        object,
        "velocityLowerBound",
        defaults.velocity_lower_bound,
    )
    .clamp(0.0, 50.0);
    PenConfig {
        color: get_int(env, object, "color", defaults.color),
        width: get_float(env, object, "width", defaults.width).max(0.001),
        min_width: get_float(env, object, "minWidth", defaults.min_width).max(0.001),
        dpi: get_float(env, object, "dpi", defaults.dpi),
        display_scale_x: get_float(env, object, "displayScaleX", defaults.display_scale_x),
        display_scale_y: get_float(env, object, "displayScaleY", defaults.display_scale_y),
        scale_precision: get_float(env, object, "scalePrecision", defaults.scale_precision),
        rotate_angle: get_int(env, object, "rotateAngle", defaults.rotate_angle),
        brush_shape: get_int(env, object, "brushShape", defaults.brush_shape),
        brush_spacing: get_float(env, object, "brushSpacing", defaults.brush_spacing)
            .clamp(0.1, 1.0),
        brush_ratio: get_float(env, object, "brushRatio", defaults.brush_ratio),
        brush_angle: get_float(env, object, "brushAngle", defaults.brush_angle),
        pressure_sensitivity: get_float(
            env,
            object,
            "pressureSensitivity",
            defaults.pressure_sensitivity,
        )
        .clamp(0.0, 1.0),
        velocity_sensitivity: get_float(
            env,
            object,
            "velocitySensitivity",
            defaults.velocity_sensitivity,
        )
        .clamp(0.0, 1.0),
        velocity_amplifier: get_float(
            env,
            object,
            "velocityAmplifier",
            defaults.velocity_amplifier,
        )
        .clamp(0.0, 1.0),
        velocity_ignore_threshold: get_float(
            env,
            object,
            "velocityIgnoreThreshold",
            defaults.velocity_ignore_threshold,
        )
        .clamp(0.0, lower_bound),
        velocity_lower_bound: lower_bound,
        velocity_upper_bound: get_float(
            env,
            object,
            "velocityUpperBound",
            defaults.velocity_upper_bound,
        )
        .clamp(0.0, 50.0),
        smooth_level: get_float(env, object, "smoothLevel", defaults.smooth_level).clamp(0.0, 1.0),
        tilt_enabled: get_bool(env, object, "tiltEnabled", defaults.tilt_enabled),
        tilt_scale: get_float(env, object, "tiltScale", defaults.tilt_scale),
        direction_enabled: get_bool(env, object, "directionEnabled", defaults.direction_enabled),
        fast_mode: get_bool(env, object, "fastMode", defaults.fast_mode),
    }
}

fn read_touches(env: &mut JNIEnv, array: JDoubleArray) -> Vec<Touch> {
    let Ok(length) = env.get_array_length(&array) else {
        return Vec::new();
    };
    let mut values = vec![0.0f64; length as usize];
    if env.get_double_array_region(&array, 0, &mut values).is_err() {
        return Vec::new();
    }
    values
        .chunks_exact(7)
        .map(|chunk| Touch {
            x: chunk[0] as f32,
            y: chunk[1] as f32,
            pressure: chunk[2] as f32,
            size: chunk[3] as f32,
            tilt_x: chunk[4] as f32,
            tilt_y: chunk[5] as f32,
            timestamp: chunk[6],
        })
        .collect()
}

fn create_bitmap<'local>(
    env: &mut JNIEnv<'local>,
    stamp: &Stamp,
    color: i32,
) -> jni::errors::Result<JObject<'local>> {
    let config_class = env.find_class("android/graphics/Bitmap$Config")?;
    let config = env
        .get_static_field(
            &config_class,
            "ARGB_8888",
            "Landroid/graphics/Bitmap$Config;",
        )?
        .l()?;
    let bitmap = env
        .call_static_method(
            "android/graphics/Bitmap",
            "createBitmap",
            "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;",
            &[
                JValue::Int(stamp.width.max(1)),
                JValue::Int(stamp.height.max(1)),
                JValue::Object(&config),
            ],
        )?
        .l()?;
    let alpha = ((color as u32 >> 24) as u8).saturating_mul(stamp.alpha) / 255;
    let argb = ((alpha as u32) << 24) | (color as u32 & 0x00ff_ffff);
    env.call_method(&bitmap, "eraseColor", "(I)V", &[JValue::Int(argb as i32)])?;
    Ok(bitmap)
}

fn build_pen_ink<'local>(
    env: &mut JNIEnv<'local>,
    ink: &InkData,
    color: i32,
) -> jni::errors::Result<JObject<'local>> {
    let points: JFloatArray = env.new_float_array(ink.points.len() as i32)?;
    env.set_float_array_region(&points, 0, &ink.points)?;
    let sizes: JIntArray = env.new_int_array(ink.point_sizes.len() as i32)?;
    env.set_int_array_region(&sizes, 0, &ink.point_sizes)?;
    let bitmaps: JObjectArray = env.new_object_array(
        ink.stamps.len() as i32,
        "android/graphics/Bitmap",
        JObject::null(),
    )?;
    for (index, stamp) in ink.stamps.iter().enumerate() {
        let bitmap = create_bitmap(env, stamp, color)?;
        env.set_object_array_element(&bitmaps, index as i32, &bitmap)?;
    }
    let points_object = JObject::from(points);
    let sizes_object = JObject::from(sizes);
    let bitmaps_object = JObject::from(bitmaps);
    env.new_object(
        "com/onyx/android/sdk/pen/PenInk",
        "([F[I[Landroid/graphics/Bitmap;)V",
        &[
            JValue::Object(&points_object),
            JValue::Object(&sizes_object),
            JValue::Object(&bitmaps_object),
        ],
    )
}

fn build_result<'local>(
    env: &mut JNIEnv<'local>,
    real: &InkData,
    prediction: &InkData,
    color: i32,
) -> jni::errors::Result<JObject<'local>> {
    let real_object = build_pen_ink(env, real, color)?;
    let prediction_object = build_pen_ink(env, prediction, color)?;
    env.new_object(
        "com/onyx/android/sdk/pen/NeoPenResult",
        "(Lcom/onyx/android/sdk/pen/PenInk;Lcom/onyx/android/sdk/pen/PenInk;)V",
        &[
            JValue::Object(&real_object),
            JValue::Object(&prediction_object),
        ],
    )
}

fn process_handle(
    handle: i64,
    touches: &[Touch],
    prediction: Option<Touch>,
    phase: Phase,
) -> Option<(InkData, InkData, i32)> {
    let mut pens = runtime().pens.lock().ok()?;
    let pen = pens.get_mut(&handle)?;
    let (real, predicted) = pen.process(touches, prediction, phase);
    let color = pen.config.color;
    if phase == Phase::Up {
        pen.reset();
    }
    Some((real, predicted, color))
}

fn throw_illegal_state(env: &mut JNIEnv, message: &str) {
    let _ = env.throw_new("java/lang/IllegalStateException", message);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeSetLogLevel(
    _env: JNIEnv,
    _object: JObject,
    level: jint,
) {
    runtime().log_level.store(level, Ordering::Relaxed);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeCreatePen(
    mut env: JNIEnv,
    _object: JObject,
    pen_type: jint,
    config: JObject,
) -> jlong {
    if !(1..=9).contains(&pen_type) || config.is_null() {
        return 0;
    }
    let handle = runtime().next_handle.fetch_add(1, Ordering::Relaxed);
    let state = PenState::new(pen_type, read_config(&mut env, &config));
    match runtime().pens.lock() {
        Ok(mut pens) => {
            pens.insert(handle, state);
            handle
        }
        Err(_) => 0,
    }
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeDestroyPen(
    _env: JNIEnv,
    _object: JObject,
    handle: jlong,
) {
    if let Ok(mut pens) = runtime().pens.lock() {
        pens.remove(&handle);
    }
}

fn render_call(
    mut env: JNIEnv,
    handle: jlong,
    points: JDoubleArray,
    prediction: Option<JDoubleArray>,
    phase: Phase,
) -> jobject {
    let touches = read_touches(&mut env, points);
    if touches.is_empty() {
        return JObject::null().into_raw();
    }
    let predicted_touch =
        prediction.and_then(|array| read_touches(&mut env, array).into_iter().next());
    let Some((real, predicted, color)) = process_handle(handle, &touches, predicted_touch, phase)
    else {
        return JObject::null().into_raw();
    };
    match build_result(&mut env, &real, &predicted, color) {
        Ok(result) => result.into_raw(),
        Err(error) => {
            throw_illegal_state(&mut env, &format!("Failed to create pen result: {error}"));
            JObject::null().into_raw()
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeOnPenDown(
    env: JNIEnv,
    _object: JObject,
    handle: jlong,
    points: JDoubleArray,
    _repaint: jboolean,
) -> jobject {
    if let Ok(mut pens) = runtime().pens.lock() {
        if let Some(pen) = pens.get_mut(&handle) {
            pen.reset();
        }
    }
    render_call(env, handle, points, None, Phase::Down)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeOnPenMove(
    env: JNIEnv,
    _object: JObject,
    handle: jlong,
    points: JDoubleArray,
    prediction: JDoubleArray,
    _repaint: jboolean,
) -> jobject {
    let prediction = (!prediction.is_null()).then_some(prediction);
    render_call(env, handle, points, prediction, Phase::Move)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeOnPenUp(
    env: JNIEnv,
    _object: JObject,
    handle: jlong,
    points: JDoubleArray,
    _repaint: jboolean,
) -> jobject {
    render_call(env, handle, points, None, Phase::Up)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenNative_nativeSetBitmapColor(
    mut env: JNIEnv,
    _object: JObject,
    bitmap: JObject,
) {
    if !bitmap.is_null() {
        let _ = env.call_method(bitmap, "setHasAlpha", "(Z)V", &[JValue::Bool(1)]);
    }
}

fn legacy_handle() -> Option<i64> {
    runtime().legacy_handle.lock().ok().and_then(|guard| *guard)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeInitPen(
    mut env: JNIEnv,
    _class: JObject,
    config: JObject,
) -> jboolean {
    if config.is_null() {
        return 0;
    }
    let pen_type = get_int(&mut env, &config, "type", 1);
    let handle = runtime().next_handle.fetch_add(1, Ordering::Relaxed);
    let state = PenState::new(pen_type, read_config(&mut env, &config));
    if let Ok(mut pens) = runtime().pens.lock() {
        pens.insert(handle, state);
    } else {
        return 0;
    }
    if let Ok(mut legacy) = runtime().legacy_handle.lock() {
        if let Some(old) = legacy.replace(handle) {
            if let Ok(mut pens) = runtime().pens.lock() {
                pens.remove(&old);
            }
        }
    }
    1
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeDestroyPen(
    _env: JNIEnv,
    _class: JObject,
) {
    if let Ok(mut legacy) = runtime().legacy_handle.lock() {
        if let Some(handle) = legacy.take() {
            if let Ok(mut pens) = runtime().pens.lock() {
                pens.remove(&handle);
            }
        }
    }
}

fn build_legacy_points<'local>(
    env: &mut JNIEnv<'local>,
    ink: &InkData,
) -> jni::errors::Result<JObjectArray<'local>> {
    let count = match ink.points.len() {
        0 => 0,
        len if !ink.stamps.is_empty() => len / 2,
        len if len % 5 == 0 => len / 5,
        len if len % 3 == 0 => len / 3,
        len => len / 2,
    };
    let array = env.new_object_array(
        count as i32,
        "com/onyx/android/sdk/pen/NeoRenderPoint",
        JObject::null(),
    )?;
    for index in 0..count {
        let (x, y, size, bitmap_index) = if !ink.stamps.is_empty() {
            let stamp = &ink.stamps[index];
            (
                ink.points[index * 2],
                ink.points[index * 2 + 1],
                stamp.width.max(stamp.height) as f32,
                index as i32,
            )
        } else if ink.points.len().is_multiple_of(5) {
            (
                ink.points[index * 5],
                ink.points[index * 5 + 1],
                ink.points[index * 5 + 2],
                0,
            )
        } else if ink.points.len().is_multiple_of(3) {
            (
                ink.points[index * 3],
                ink.points[index * 3 + 1],
                ink.points[index * 3 + 2],
                0,
            )
        } else {
            (ink.points[index * 2], ink.points[index * 2 + 1], 1.0, 0)
        };
        let point = env.new_object("com/onyx/android/sdk/pen/NeoRenderPoint", "()V", &[])?;
        env.set_field(&point, "x", "F", JValue::Float(x))?;
        env.set_field(&point, "y", "F", JValue::Float(y))?;
        env.set_field(&point, "size", "F", JValue::Float(size))?;
        env.set_field(&point, "bitmapIndex", "I", JValue::Int(bitmap_index))?;
        env.set_object_array_element(&array, index as i32, &point)?;
    }
    Ok(array)
}

fn legacy_call(mut env: JNIEnv, points: JDoubleArray, phase: Phase) -> jobjectArray {
    let Some(handle) = legacy_handle() else {
        return JObjectArray::from(JObject::null()).into_raw();
    };
    let touches = read_touches(&mut env, points);
    let Some((ink, _, _)) = process_handle(handle, &touches, None, phase) else {
        return JObjectArray::from(JObject::null()).into_raw();
    };
    if let Ok(mut stamps) = runtime().legacy_bitmaps.lock() {
        *stamps = ink.stamps.clone();
    }
    build_legacy_points(&mut env, &ink)
        .map(JObjectArray::into_raw)
        .unwrap_or_else(|_| JObjectArray::from(JObject::null()).into_raw())
}

fn legacy_compute(mut env: JNIEnv, points: JDoubleArray) -> jobjectArray {
    let Some(handle) = legacy_handle() else {
        return JObjectArray::from(JObject::null()).into_raw();
    };
    let touches = read_touches(&mut env, points);
    if touches.is_empty() {
        return env
            .new_object_array(
                0,
                "com/onyx/android/sdk/pen/NeoRenderPoint",
                JObject::null(),
            )
            .map(JObjectArray::into_raw)
            .unwrap_or_else(|_| JObjectArray::from(JObject::null()).into_raw());
    }

    let mut combined = InkData::default();
    let append = |target: &mut InkData, source: InkData| {
        target.points.extend(source.points);
        target.point_sizes.extend(source.point_sizes);
        target.stamps.extend(source.stamps);
    };
    if let Some((down, _, _)) = process_handle(handle, &touches[..1], None, Phase::Down) {
        append(&mut combined, down);
    }
    if touches.len() > 2 {
        if let Some((moves, _, _)) =
            process_handle(handle, &touches[1..touches.len() - 1], None, Phase::Move)
        {
            append(&mut combined, moves);
        }
    }
    if touches.len() > 1 {
        if let Some((up, _, _)) =
            process_handle(handle, &touches[touches.len() - 1..], None, Phase::Up)
        {
            append(&mut combined, up);
        }
    }
    if let Ok(mut stamps) = runtime().legacy_bitmaps.lock() {
        *stamps = combined.stamps.clone();
    }
    build_legacy_points(&mut env, &combined)
        .map(JObjectArray::into_raw)
        .unwrap_or_else(|_| JObjectArray::from(JObject::null()).into_raw())
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeOnPenDown(
    env: JNIEnv,
    _class: JObject,
    points: JDoubleArray,
) -> jobjectArray {
    legacy_call(env, points, Phase::Down)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeOnPenMove(
    env: JNIEnv,
    _class: JObject,
    points: JDoubleArray,
) -> jobjectArray {
    legacy_call(env, points, Phase::Move)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeOnPenUp(
    env: JNIEnv,
    _class: JObject,
    points: JDoubleArray,
) -> jobjectArray {
    legacy_call(env, points, Phase::Up)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeComputeRenderPoints(
    env: JNIEnv,
    _class: JObject,
    points: JDoubleArray,
) -> jobjectArray {
    legacy_compute(env, points)
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_NeoPenWrapper_nativeGetRenderedBitmaps(
    mut env: JNIEnv,
    _class: JObject,
) -> jobjectArray {
    let stamps = runtime()
        .legacy_bitmaps
        .lock()
        .map(|value| value.clone())
        .unwrap_or_default();
    let color = legacy_handle()
        .and_then(|handle| {
            runtime()
                .pens
                .lock()
                .ok()?
                .get(&handle)
                .map(|pen| pen.config.color)
        })
        .unwrap_or(0xff00_0000u32 as i32);
    let Ok(array) = env.new_object_array(
        stamps.len() as i32,
        "android/graphics/Bitmap",
        JObject::null(),
    ) else {
        return JObjectArray::from(JObject::null()).into_raw();
    };
    for (index, stamp) in stamps.iter().enumerate() {
        if let Ok(bitmap) = create_bitmap(&mut env, stamp, color) {
            let _ = env.set_object_array_element(&array, index as i32, bitmap);
        }
    }
    array.into_raw()
}

#[cfg(test)]
mod tests {
    use super::*;

    fn touch(x: f32, y: f32, pressure: f32, timestamp: f64) -> Touch {
        Touch {
            x,
            y,
            pressure,
            timestamp,
            ..Touch::default()
        }
    }

    #[test]
    fn all_pen_types_emit_the_expected_encoding() {
        for pen_type in 1..=9 {
            let mut pen = PenState::new(pen_type, PenConfig::default());
            let (down, _) = pen.process(&[touch(10.0, 20.0, 0.5, 1.0)], None, Phase::Down);
            let (move_ink, _) = pen.process(&[touch(15.0, 25.0, 0.6, 2.0)], None, Phase::Move);
            let (up, _) = pen.process(&[touch(20.0, 30.0, 0.4, 3.0)], None, Phase::Up);
            assert!(
                !down.points.is_empty() || !move_ink.points.is_empty() || !up.points.is_empty(),
                "pen type {pen_type}"
            );
        }
    }

    #[test]
    fn prediction_does_not_advance_committed_state() {
        let mut pen = PenState::new(2, PenConfig::default());
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        let committed = pen.last;
        let (_, prediction) = pen.process(&[], Some(touch(20.0, 0.0, 0.5, 2.0)), Phase::Move);
        assert!(prediction.points.is_empty());
        assert_eq!(pen.last, committed);
    }

    #[test]
    fn finish_resets_stroke_state() {
        let mut pen = PenState::new(3, PenConfig::default());
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        pen.reset();
        assert_eq!(pen.last, None);
    }

    #[test]
    fn pressure_and_velocity_widths_are_bounded() {
        let pen = PenState::new(1, PenConfig::default());
        let low = pen.width_for(None, touch(0.0, 0.0, 0.0, 1.0));
        let high = pen.width_for(None, touch(0.0, 0.0, 1.0, 1.0));
        assert!(low >= pen.config.min_width);
        assert!(high <= pen.config.width);
        assert!(high > low);
    }
}

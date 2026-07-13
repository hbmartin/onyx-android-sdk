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
    renderer_version: i32,
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
            renderer_version: 1,
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

impl PenConfig {
    fn sanitized(mut self) -> Self {
        let defaults = Self::default();
        self.renderer_version = self.renderer_version.clamp(1, 2);
        self.width = finite_or(self.width, defaults.width).max(0.001);
        self.min_width = finite_or(self.min_width, defaults.min_width).max(0.001);
        self.dpi = finite_or(self.dpi, defaults.dpi);
        self.display_scale_x = finite_or(self.display_scale_x, defaults.display_scale_x);
        self.display_scale_y = finite_or(self.display_scale_y, defaults.display_scale_y);
        self.scale_precision = finite_or(self.scale_precision, defaults.scale_precision);
        self.brush_spacing = finite_or(self.brush_spacing, defaults.brush_spacing).clamp(0.1, 1.0);
        self.brush_ratio = finite_or(self.brush_ratio, defaults.brush_ratio);
        self.brush_angle = finite_or(self.brush_angle, defaults.brush_angle);
        self.pressure_sensitivity =
            finite_or(self.pressure_sensitivity, defaults.pressure_sensitivity).clamp(0.0, 1.0);
        self.velocity_sensitivity =
            finite_or(self.velocity_sensitivity, defaults.velocity_sensitivity).clamp(0.0, 1.0);
        self.velocity_amplifier =
            finite_or(self.velocity_amplifier, defaults.velocity_amplifier).clamp(0.0, 1.0);
        self.velocity_lower_bound =
            finite_or(self.velocity_lower_bound, defaults.velocity_lower_bound).clamp(0.0, 50.0);
        self.velocity_upper_bound =
            finite_or(self.velocity_upper_bound, defaults.velocity_upper_bound).clamp(0.0, 50.0);
        self.velocity_ignore_threshold = finite_or(
            self.velocity_ignore_threshold,
            defaults.velocity_ignore_threshold,
        )
        .clamp(0.0, self.velocity_lower_bound);
        self.smooth_level = finite_or(self.smooth_level, defaults.smooth_level).clamp(0.0, 1.0);
        self.tilt_scale = finite_or(self.tilt_scale, defaults.tilt_scale);
        self
    }
}

fn finite_or(value: f32, fallback: f32) -> f32 {
    if value.is_finite() {
        value
    } else {
        fallback
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
        let config = config.sanitized();
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
        if !point.pressure.is_finite() {
            point.pressure = 0.0;
        }
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

    fn reference_fountain_spacing(&self) -> f32 {
        let dpi_scale = (self.config.dpi / 320.0).clamp(0.5, 4.0);
        let precision = self.config.scale_precision.clamp(1.0, 8.0).sqrt();
        let fast_scale = if self.config.fast_mode { 1.5 } else { 1.0 };
        (self.config.brush_spacing * 4.0 * dpi_scale * fast_scale / precision).clamp(0.25, 16.0)
    }

    fn segment_velocity(start: Touch, end: Touch) -> f32 {
        let distance = ((end.x - start.x).powi(2) + (end.y - start.y).powi(2)).sqrt();
        let elapsed = (end.timestamp - start.timestamp).abs() as f32;
        if elapsed <= 0.001 {
            distance
        } else {
            distance / elapsed
        }
    }

    fn normalized_velocity(&self, velocity: f32) -> f32 {
        if velocity <= self.config.velocity_ignore_threshold {
            return 0.0;
        }
        let lower = self.config.velocity_lower_bound;
        let upper = self.config.velocity_upper_bound;
        if upper > lower + f32::EPSILON {
            ((velocity - lower) / (upper - lower)).clamp(0.0, 1.0)
        } else {
            (velocity / (velocity + 1.0)).clamp(0.0, 1.0)
        }
    }

    fn reference_fountain_width(
        &self,
        previous: Option<Touch>,
        point: Touch,
        velocity: f32,
    ) -> f32 {
        let min_width = self.config.min_width.max(0.001);
        // Reverse-engineering notes indicate the native renderer normalizes
        // the configured width before applying pressure and velocity.
        let normalized_width = (self.config.width + 3.0) * 0.5;
        let max_width = (normalized_width * 2.0).max(min_width);
        let pressure_exponent = 1.5 - self.config.pressure_sensitivity.clamp(0.0, 1.0);
        let pressure = point.pressure.clamp(0.0, 1.0).powf(pressure_exponent);
        let mut width = min_width + (max_width - min_width) * pressure;

        let velocity_factor = self.normalized_velocity(velocity);
        width *= 1.0 - velocity_factor * self.config.velocity_sensitivity.clamp(0.0, 1.0) * 0.65;
        width *= 1.0 + velocity_factor * self.config.velocity_amplifier.clamp(0.0, 1.0) * 0.35;

        if self.config.tilt_enabled {
            let tilt = (point.tilt_x.powi(2) + point.tilt_y.powi(2)).sqrt();
            let normalized_tilt = (tilt / 90.0).clamp(0.0, 1.0);
            width *= 1.0 + normalized_tilt * self.config.tilt_scale.clamp(0.0, 10.0) * 0.05;
        }

        if self.config.direction_enabled {
            if let Some(previous) = previous {
                let direction = (point.y - previous.y).atan2(point.x - previous.x);
                let configured_angle = self.config.brush_angle.to_radians()
                    + (self.config.rotate_angle as f32).to_radians();
                let directional = (direction - configured_angle).sin().abs();
                let ratio = self.config.brush_ratio.clamp(1.0, 10.0);
                width *= 1.0 + directional * (ratio - 1.0) * 0.05;
            }
        }

        let shape_factor = match self.config.brush_shape {
            1 => 0.95,
            2 => 1.05,
            _ => 1.0,
        };
        (width * shape_factor).clamp(min_width, max_width * 1.5)
    }

    fn smooth_reference_fountain_point(&self, point: Touch) -> Touch {
        let Some(previous) = self.last else {
            return point;
        };
        let current_weight = 1.0 - self.config.smooth_level.clamp(0.0, 1.0) * 0.85;
        let previous_weight = 1.0 - current_weight;
        Touch {
            x: previous.x * previous_weight + point.x * current_weight,
            y: previous.y * previous_weight + point.y * current_weight,
            pressure: previous.pressure * previous_weight + point.pressure * current_weight,
            size: previous.size * previous_weight + point.size * current_weight,
            tilt_x: previous.tilt_x * previous_weight + point.tilt_x * current_weight,
            tilt_y: previous.tilt_y * previous_weight + point.tilt_y * current_weight,
            timestamp: point.timestamp,
        }
    }

    fn emit_reference_fountain(&mut self, input: &[Touch]) -> InkData {
        let mut ink = InkData::default();
        for point in input.iter().copied() {
            let smoothed = self.smooth_reference_fountain_point(point);
            let previous = self.last;
            let velocity = previous
                .map(|start| Self::segment_velocity(start, smoothed))
                .unwrap_or(0.0);
            let target_width = self.reference_fountain_width(previous, smoothed, velocity);
            if let Some(start) = previous {
                let distance =
                    ((smoothed.x - start.x).powi(2) + (smoothed.y - start.y).powi(2)).sqrt();
                let steps = (distance / self.reference_fountain_spacing())
                    .ceil()
                    .clamp(1.0, 4096.0) as usize;
                for (index, sample) in Self::interpolate_linear(start, smoothed, steps)
                    .into_iter()
                    .enumerate()
                {
                    let t = (index + 1) as f32 / steps as f32;
                    let width = self.last_width + (target_width - self.last_width) * t;
                    Self::push_point(&mut ink, sample, width);
                }
            } else {
                Self::push_point(&mut ink, smoothed, target_width);
            }
            self.last_width = target_width;
            self.last = Some(smoothed);
            self.history.push(smoothed);
            if self.history.len() > 32 {
                self.history.remove(0);
            }
        }
        ink
    }

    fn process_reference_fountain(
        &mut self,
        input: &[Touch],
        prediction: Option<Touch>,
        phase: Phase,
    ) -> (InkData, InkData) {
        if phase == Phase::Down {
            self.reset();
        }
        let real = self.emit_reference_fountain(input);
        let predicted = if phase == Phase::Move {
            prediction
                .map(|point| {
                    let mut scratch = self.clone();
                    scratch.emit_reference_fountain(&[point])
                })
                .unwrap_or_default()
        } else {
            InkData::default()
        };
        (real, predicted)
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
            // Floating-point rounding can push the last target past the total
            // distance; on a degenerate final segment the raw ratio then far
            // exceeds 1 and would index outside the interpolation buffer.
            let t = ((target - distances[segment]) / span).clamp(0.0, 1.0);
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
        // Non-finite coordinates would otherwise flow into every downstream
        // width/step computation and the committed float records.
        let transformed = input
            .iter()
            .map(|point| self.transform(*point))
            .filter(|point| point.x.is_finite() && point.y.is_finite())
            .collect::<Vec<_>>();
        let mut real = InkData::default();
        let mut predicted = InkData::default();
        if self.pen_type == 2 && self.config.renderer_version == 2 {
            let prediction = prediction
                .map(|point| self.transform(point))
                .filter(|point| point.x.is_finite() && point.y.is_finite());
            return self.process_reference_fountain(&transformed, prediction, phase);
        }
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
                            // curve below.  The relaxation is capped at the same upper bound
                            // as brush_width so long strokes cannot grow without limit.
                            self.last_width =
                                (self.last_width * 1.00375).min(self.config.width * 2.0 + 3.0);
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
                                // Cap keeps a single absurd coordinate jump from
                                // allocating an unbounded interpolation buffer.
                                let steps = (distance / 4.0).ceil().clamp(1.0, 4096.0) as usize;
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
                        if let Some(point) = previous_history.last().copied() {
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
                        let steps = (distance / 3.0).ceil().clamp(1.0, 4096.0) as usize;
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
                        let point = self.history.last().copied().unwrap_or(end);
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
                        if points.len() < 2 {
                            // Up without a preceding down: nothing to interpolate.
                            Self::push_point(&mut real, end, 0.5 + 2.5 * end.pressure);
                        } else {
                            for pair in
                                [(0.22, 0), (0.86, 1), (0.73, points.len().saturating_sub(2))]
                            {
                                let index = pair.1.min(points.len().saturating_sub(2));
                                let point =
                                    Self::interpolate_linear(points[index], points[index + 1], 100)
                                        [(pair.0 * 99.0) as usize];
                                Self::push_point(&mut real, point, 0.5 + 2.5 * point.pressure);
                            }
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
    PenConfig {
        renderer_version: get_int(env, object, "rendererVersion", defaults.renderer_version)
            .clamp(1, 2),
        color: get_int(env, object, "color", defaults.color),
        width: get_float(env, object, "width", defaults.width),
        min_width: get_float(env, object, "minWidth", defaults.min_width),
        dpi: get_float(env, object, "dpi", defaults.dpi),
        display_scale_x: get_float(env, object, "displayScaleX", defaults.display_scale_x),
        display_scale_y: get_float(env, object, "displayScaleY", defaults.display_scale_y),
        scale_precision: get_float(env, object, "scalePrecision", defaults.scale_precision),
        rotate_angle: get_int(env, object, "rotateAngle", defaults.rotate_angle),
        brush_shape: get_int(env, object, "brushShape", defaults.brush_shape),
        brush_spacing: get_float(env, object, "brushSpacing", defaults.brush_spacing),
        brush_ratio: get_float(env, object, "brushRatio", defaults.brush_ratio),
        brush_angle: get_float(env, object, "brushAngle", defaults.brush_angle),
        pressure_sensitivity: get_float(
            env,
            object,
            "pressureSensitivity",
            defaults.pressure_sensitivity,
        ),
        velocity_sensitivity: get_float(
            env,
            object,
            "velocitySensitivity",
            defaults.velocity_sensitivity,
        ),
        velocity_amplifier: get_float(
            env,
            object,
            "velocityAmplifier",
            defaults.velocity_amplifier,
        ),
        velocity_ignore_threshold: get_float(
            env,
            object,
            "velocityIgnoreThreshold",
            defaults.velocity_ignore_threshold,
        ),
        velocity_lower_bound: get_float(
            env,
            object,
            "velocityLowerBound",
            defaults.velocity_lower_bound,
        ),
        velocity_upper_bound: get_float(
            env,
            object,
            "velocityUpperBound",
            defaults.velocity_upper_bound,
        ),
        smooth_level: get_float(env, object, "smoothLevel", defaults.smooth_level),
        tilt_enabled: get_bool(env, object, "tiltEnabled", defaults.tilt_enabled),
        tilt_scale: get_float(env, object, "tiltScale", defaults.tilt_scale),
        direction_enabled: get_bool(env, object, "directionEnabled", defaults.direction_enabled),
        fast_mode: get_bool(env, object, "fastMode", defaults.fast_mode),
    }
    .sanitized()
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

fn scale_color_alpha(color: i32, stamp_alpha: u8) -> i32 {
    let alpha = (color as u32 >> 24) * u32::from(stamp_alpha) / 255;
    ((alpha << 24) | (color as u32 & 0x00ff_ffff)) as i32
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
    let argb = scale_color_alpha(color, stamp.alpha);
    env.call_method(&bitmap, "eraseColor", "(I)V", &[JValue::Int(argb)])?;
    env.delete_local_ref(config_class)?;
    env.delete_local_ref(config)?;
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
        env.delete_local_ref(bitmap)?;
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

/// Decodes ink records into legacy (x, y, size, bitmapIndex) tuples using the
/// exact per-record arity in `point_sizes`; the float-count divisibility of a
/// mixed or repeated stream is ambiguous and must never be guessed from.
fn legacy_records(ink: &InkData) -> Vec<(f32, f32, f32, i32)> {
    let mut records = Vec::with_capacity(ink.point_sizes.len());
    let mut offset = 0usize;
    let mut stamp_index = 0usize;
    for &size in &ink.point_sizes {
        let size = size.max(0) as usize;
        if size == 0 || offset + size > ink.points.len() {
            break;
        }
        let chunk = &ink.points[offset..offset + size];
        offset += size;
        match size {
            2 => {
                let dimension = ink
                    .stamps
                    .get(stamp_index)
                    .map(|stamp| stamp.width.max(stamp.height) as f32)
                    .unwrap_or(1.0);
                records.push((chunk[0], chunk[1], dimension, stamp_index as i32));
                stamp_index += 1;
            }
            3 | 5 => records.push((chunk[0], chunk[1], chunk[2], 0)),
            // Polygon record: indices 4/7 hold the segment end centre and
            // 6 - 4 recovers the half width used by the encoder.
            12 => records.push((chunk[4], chunk[7], (chunk[6] - chunk[4]).abs() * 2.0, 0)),
            _ => {}
        }
    }
    records
}

fn build_legacy_points<'local>(
    env: &mut JNIEnv<'local>,
    ink: &InkData,
) -> jni::errors::Result<JObjectArray<'local>> {
    let records = legacy_records(ink);
    let array = env.new_object_array(
        records.len() as i32,
        "com/onyx/android/sdk/pen/NeoRenderPoint",
        JObject::null(),
    )?;
    for (index, (x, y, size, bitmap_index)) in records.into_iter().enumerate() {
        let point = env.new_object("com/onyx/android/sdk/pen/NeoRenderPoint", "()V", &[])?;
        env.set_field(&point, "x", "F", JValue::Float(x))?;
        env.set_field(&point, "y", "F", JValue::Float(y))?;
        env.set_field(&point, "size", "F", JValue::Float(size))?;
        env.set_field(&point, "bitmapIndex", "I", JValue::Int(bitmap_index))?;
        env.set_object_array_element(&array, index as i32, &point)?;
        env.delete_local_ref(point)?;
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

    // Offline rendering runs on a scratch copy of the legacy pen so an
    // in-progress interactive stroke on the shared handle is not reset.
    let Some(mut scratch) = runtime()
        .pens
        .lock()
        .ok()
        .and_then(|pens| pens.get(&handle).cloned())
    else {
        return JObjectArray::from(JObject::null()).into_raw();
    };
    scratch.reset();
    let mut combined = InkData::default();
    let append = |target: &mut InkData, source: InkData| {
        target.points.extend(source.points);
        target.point_sizes.extend(source.point_sizes);
        target.stamps.extend(source.stamps);
    };
    let (down, _) = scratch.process(&touches[..1], None, Phase::Down);
    append(&mut combined, down);
    if touches.len() > 2 {
        let (moves, _) = scratch.process(&touches[1..touches.len() - 1], None, Phase::Move);
        append(&mut combined, moves);
    }
    if touches.len() > 1 {
        let (up, _) = scratch.process(&touches[touches.len() - 1..], None, Phase::Up);
        append(&mut combined, up);
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
        // A failed bitmap leaves a pending Java exception; issuing further
        // JNI calls in that state is illegal, so surface it to the caller.
        match create_bitmap(&mut env, stamp, color) {
            Ok(bitmap) => {
                if env
                    .set_object_array_element(&array, index as i32, &bitmap)
                    .is_err()
                {
                    return JObjectArray::from(JObject::null()).into_raw();
                }
                let _ = env.delete_local_ref(bitmap);
            }
            Err(_) => return JObjectArray::from(JObject::null()).into_raw(),
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
        let expected_arities: [(i32, &[i32]); 9] = [
            (1, &[3]),
            (2, &[3]),
            (3, &[3]),
            (4, &[2]),
            (5, &[2]),
            (6, &[12]),
            (7, &[5]),
            (8, &[12]),
            (9, &[12]),
        ];
        for (pen_type, arities) in expected_arities {
            let mut pen = PenState::new(pen_type, PenConfig::default());
            let (down, _) = pen.process(&[touch(10.0, 20.0, 0.5, 1.0)], None, Phase::Down);
            let (first_move, _) = pen.process(&[touch(15.0, 25.0, 0.6, 2.0)], None, Phase::Move);
            let (second_move, _) = pen.process(&[touch(30.0, 40.0, 0.6, 3.0)], None, Phase::Move);
            let (up, _) = pen.process(&[touch(45.0, 55.0, 0.4, 4.0)], None, Phase::Up);
            let mut produced = false;
            for ink in [&down, &first_move, &second_move, &up] {
                produced |= !ink.points.is_empty();
                let float_count = ink.point_sizes.iter().map(|size| *size as usize).sum();
                assert_eq!(
                    ink.points.len(),
                    float_count,
                    "pen {pen_type}: floats must match declared record arities"
                );
                let stamp_records = ink.point_sizes.iter().filter(|size| **size == 2).count();
                assert_eq!(
                    ink.stamps.len(),
                    stamp_records,
                    "pen {pen_type}: one stamp per 2-value record"
                );
                for size in &ink.point_sizes {
                    assert!(
                        arities.contains(size),
                        "pen {pen_type} emitted unexpected arity {size}"
                    );
                }
                for value in &ink.points {
                    assert!(value.is_finite(), "pen {pen_type} emitted non-finite value");
                }
            }
            assert!(produced, "pen type {pen_type} emitted nothing");
        }
    }

    #[test]
    fn prediction_never_advances_committed_state() {
        let config = PenConfig::default();
        let mut with_prediction = PenState::new(7, config.clone());
        let mut without_prediction = PenState::new(7, config);
        let down = [touch(0.0, 0.0, 0.5, 1.0)];
        let moves = [touch(12.0, 5.0, 0.6, 2.0), touch(24.0, 9.0, 0.7, 3.0)];
        let up = [touch(30.0, 12.0, 0.4, 5.0)];

        let (down_a, _) = with_prediction.process(&down, None, Phase::Down);
        let (down_b, _) = without_prediction.process(&down, None, Phase::Down);
        assert_eq!(down_a.points, down_b.points);

        let prediction = Some(touch(40.0, 16.0, 0.7, 4.0));
        let (move_a, predicted) = with_prediction.process(&moves, prediction, Phase::Move);
        let (move_b, _) = without_prediction.process(&moves, None, Phase::Move);
        assert!(
            !predicted.points.is_empty(),
            "pencil must emit prediction ink"
        );
        assert_eq!(
            move_a.points, move_b.points,
            "prediction must not alter committed move ink"
        );
        assert_eq!(with_prediction.history, without_prediction.history);
        assert_eq!(with_prediction.last, without_prediction.last);

        let (up_a, _) = with_prediction.process(&up, None, Phase::Up);
        let (up_b, _) = without_prediction.process(&up, None, Phase::Up);
        assert_eq!(
            up_a.points, up_b.points,
            "prediction must not affect the finished stroke"
        );
    }

    #[test]
    fn recovered_renderer_remains_the_default() {
        let config = PenConfig::default();
        assert_eq!(config.renderer_version, 1);
        let mut pen = PenState::new(2, config);
        let (down, _) = pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        assert_eq!(down.points, vec![0.0, 0.0, 3.0]);
    }

    #[test]
    fn reference_fountain_uses_velocity_pressure_tilt_and_smoothing() {
        let base = PenConfig {
            renderer_version: 2,
            pressure_sensitivity: 0.8,
            velocity_sensitivity: 1.0,
            velocity_lower_bound: 0.0,
            velocity_upper_bound: 20.0,
            smooth_level: 0.5,
            tilt_enabled: true,
            tilt_scale: 5.0,
            ..PenConfig::default()
        };
        let mut slow = PenState::new(2, base.clone());
        let mut fast = PenState::new(2, base.clone());
        slow.process(&[touch(0.0, 0.0, 0.4, 0.0)], None, Phase::Down);
        fast.process(&[touch(0.0, 0.0, 0.4, 0.0)], None, Phase::Down);
        let (slow_ink, _) = slow.process(&[touch(10.0, 0.0, 0.8, 10.0)], None, Phase::Move);
        let (fast_ink, _) = fast.process(&[touch(10.0, 0.0, 0.8, 1.0)], None, Phase::Move);
        assert!(slow_ink.points.last().unwrap() > fast_ink.points.last().unwrap());
        assert!(
            slow.last.unwrap().x < 10.0,
            "smoothing must lag the raw point"
        );

        let mut untilted = PenState::new(2, base.clone());
        let mut tilted = PenState::new(2, base);
        let plain = touch(0.0, 0.0, 0.7, 1.0);
        let mut angled = plain;
        angled.tilt_x = 60.0;
        let (plain_ink, _) = untilted.process(&[plain], None, Phase::Down);
        let (tilted_ink, _) = tilted.process(&[angled], None, Phase::Down);
        assert!(tilted_ink.points[2] > plain_ink.points[2]);
    }

    #[test]
    fn reference_fountain_prediction_is_isolated_and_batch_invariant() {
        let config = PenConfig {
            renderer_version: 2,
            ..PenConfig::default()
        };
        let down = [touch(0.0, 0.0, 0.5, 1.0)];
        let moves = [touch(8.0, 3.0, 0.6, 2.0), touch(16.0, 7.0, 0.7, 3.0)];
        let prediction = touch(24.0, 10.0, 0.6, 4.0);

        let mut predicted_pen = PenState::new(2, config.clone());
        let mut plain_pen = PenState::new(2, config.clone());
        predicted_pen.process(&down, None, Phase::Down);
        plain_pen.process(&down, None, Phase::Down);
        let (predicted_real, predicted) =
            predicted_pen.process(&moves, Some(prediction), Phase::Move);
        let (plain_real, _) = plain_pen.process(&moves, None, Phase::Move);
        assert!(!predicted.points.is_empty());
        assert_eq!(predicted_real.points, plain_real.points);
        assert_eq!(predicted_pen.history, plain_pen.history);

        let mut batched = PenState::new(2, config.clone());
        let mut split = PenState::new(2, config);
        batched.process(&down, None, Phase::Down);
        split.process(&down, None, Phase::Down);
        let (batched_ink, _) = batched.process(&moves, None, Phase::Move);
        let (first, _) = split.process(&moves[..1], None, Phase::Move);
        let (second, _) = split.process(&moves[1..], None, Phase::Move);
        let mut split_points = first.points;
        split_points.extend(second.points);
        assert_eq!(batched_ink.points, split_points);
        assert_eq!(batched.history, split.history);
    }

    #[test]
    fn reference_fountain_configuration_boundaries_stay_finite() {
        for value in [0.0, 1.0] {
            let config = PenConfig {
                renderer_version: 2,
                pressure_sensitivity: value,
                velocity_sensitivity: value,
                velocity_amplifier: value,
                smooth_level: value,
                brush_spacing: if value == 0.0 { 0.1 } else { 1.0 },
                scale_precision: if value == 0.0 { 1.0 } else { 8.0 },
                ..PenConfig::default()
            };
            let mut pen = PenState::new(2, config);
            pen.process(&[touch(0.0, 0.0, 0.0, 0.0)], None, Phase::Down);
            let (ink, _) = pen.process(&[touch(100.0, 50.0, 1.0, 1.0)], None, Phase::Up);
            assert!(ink.points.iter().all(|item| item.is_finite()));
            assert!(ink.point_sizes.iter().all(|size| *size == 3));
        }
    }

    #[test]
    fn finish_resets_stroke_state() {
        let mut pen = PenState::new(3, PenConfig::default());
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        pen.reset();
        assert_eq!(pen.last, None);
    }

    #[test]
    fn brush_width_stays_bounded_on_long_strokes() {
        let mut pen = PenState::new(1, PenConfig::default());
        let cap = pen.config.width * 2.0 + 3.0;
        pen.process(&[touch(0.0, 0.0, 0.9, 1.0)], None, Phase::Down);
        let mut widths = Vec::new();
        for index in 0..5000 {
            let position = index as f32;
            let (ink, _) = pen.process(
                &[touch(position, position, 0.9, f64::from(position) + 2.0)],
                None,
                Phase::Move,
            );
            widths.extend(ink.points.chunks_exact(3).map(|record| record[2]));
        }
        assert!(!widths.is_empty());
        for width in widths {
            assert!(width.is_finite());
            assert!(width <= cap + 1e-3, "width {width} exceeds cap {cap}");
        }
    }

    #[test]
    fn pencil_up_with_degenerate_tail_is_safe() {
        let mut pen = PenState::new(7, PenConfig::default());
        pen.process(&[touch(0.3, 0.7, 0.5, 1.0)], None, Phase::Down);
        pen.process(&[touch(10.1, 7.3, 0.6, 2.0)], None, Phase::Move);
        // Up repeating the previous point creates a zero-length final segment,
        // the shape that used to index outside the interpolation buffer.
        let (ink, _) = pen.process(&[touch(10.1, 7.3, 0.6, 3.0)], None, Phase::Up);
        assert_eq!(ink.point_sizes.len(), 31);
        assert!(ink.point_sizes.iter().all(|size| *size == 5));
        assert!(ink.points.iter().all(|value| value.is_finite()));
    }

    #[test]
    fn fast_mode_up_without_down_is_safe() {
        let mut pen = PenState::new(
            6,
            PenConfig {
                fast_mode: true,
                ..PenConfig::default()
            },
        );
        let (ink, _) = pen.process(&[touch(5.0, 6.0, 0.5, 1.0)], None, Phase::Up);
        assert_eq!(ink.point_sizes, vec![3]);
    }

    #[test]
    fn legacy_decoding_uses_exact_record_arities() {
        // Five 3-value records are 15 floats — also divisible by five, the
        // exact stream the old divisibility heuristic misparsed.
        let point_pen_ink = InkData {
            points: (0..15).map(|value| value as f32).collect(),
            point_sizes: vec![3; 5],
            stamps: Vec::new(),
        };
        let records = legacy_records(&point_pen_ink);
        assert_eq!(records.len(), 5);
        assert_eq!(records[1], (3.0, 4.0, 5.0, 0));

        let mixed_ink = InkData {
            points: vec![1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0],
            point_sizes: vec![2, 5],
            stamps: vec![Stamp {
                width: 13,
                height: 14,
                alpha: 180,
            }],
        };
        assert_eq!(
            legacy_records(&mixed_ink),
            vec![(1.0, 2.0, 14.0, 0), (3.0, 4.0, 5.0, 0)]
        );
    }

    #[test]
    fn stamp_alpha_scales_the_configured_color() {
        assert_eq!(
            scale_color_alpha(0x8012_3456u32 as i32, 180),
            0x5a12_3456u32 as i32
        );
    }

    #[test]
    fn non_finite_and_absurd_inputs_are_bounded() {
        let mut pen = PenState::new(2, PenConfig::default());
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        let (ink, _) = pen.process(
            &[
                touch(f32::NAN, 4.0, 0.5, 2.0),
                touch(8.0, f32::INFINITY, 0.5, 3.0),
                touch(1.0e9, 0.0, f32::NAN, 4.0),
            ],
            None,
            Phase::Move,
        );
        assert!(ink.points.iter().all(|value| value.is_finite()));
        assert!(ink.point_sizes.len() <= 4096);
    }

    #[test]
    fn stale_and_double_destroyed_handles_are_safe() {
        let handle = runtime().next_handle.fetch_add(1, Ordering::Relaxed);
        runtime()
            .pens
            .lock()
            .unwrap()
            .insert(handle, PenState::new(1, PenConfig::default()));
        assert!(process_handle(handle, &[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down).is_some());
        assert!(runtime().pens.lock().unwrap().remove(&handle).is_some());
        assert!(process_handle(handle, &[touch(1.0, 1.0, 0.5, 2.0)], None, Phase::Move).is_none());
        assert!(runtime().pens.lock().unwrap().remove(&handle).is_none());
    }

    #[test]
    fn charcoal_stamps_track_the_stroke() {
        let mut pen = PenState::new(4, PenConfig::default());
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        let (first_move, _) = pen.process(&[touch(50.0, 50.0, 0.5, 2.0)], None, Phase::Move);
        let (second_move, _) = pen.process(&[touch(100.0, 100.0, 0.5, 3.0)], None, Phase::Move);
        assert_eq!(first_move.points[0], -3.0);
        assert_eq!(second_move.points[0], 47.0);
        assert_eq!(first_move.stamps[0].alpha, 180);

        let mut v2 = PenState::new(5, PenConfig::default());
        v2.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        v2.process(&[touch(60.0, 0.0, 0.5, 2.0)], None, Phase::Move);
        let (up, _) = v2.process(&[touch(60.0, 0.0, 0.5, 3.0)], None, Phase::Up);
        assert_eq!(up.points, [56.0, -4.0]);
        assert_eq!(up.stamps[0].alpha, 180);
    }

    #[test]
    fn reference_fountain_sanitizes_non_finite_configuration() {
        let config = PenConfig {
            renderer_version: 2,
            dpi: f32::NAN,
            scale_precision: f32::NAN,
            brush_spacing: f32::NAN,
            brush_ratio: f32::NAN,
            brush_angle: f32::NAN,
            pressure_sensitivity: f32::NAN,
            velocity_sensitivity: f32::NAN,
            velocity_amplifier: f32::NAN,
            velocity_ignore_threshold: f32::NAN,
            velocity_lower_bound: f32::NAN,
            velocity_upper_bound: f32::NAN,
            smooth_level: f32::NAN,
            tilt_enabled: true,
            tilt_scale: f32::NAN,
            direction_enabled: true,
            ..PenConfig::default()
        };
        let mut pen = PenState::new(2, config);
        pen.process(&[touch(0.0, 0.0, 0.5, 1.0)], None, Phase::Down);
        let (ink, _) = pen.process(&[touch(20.0, 10.0, 0.8, 2.0)], None, Phase::Up);
        assert!(!ink.points.is_empty());
        assert!(ink.points.iter().all(|value| value.is_finite()));
    }
}

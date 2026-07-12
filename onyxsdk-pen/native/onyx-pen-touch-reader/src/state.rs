pub const EV_SYN: u16 = 0;
pub const EV_KEY: u16 = 1;
pub const EV_ABS: u16 = 3;
pub const ABS_X: u16 = 0;
pub const ABS_Y: u16 = 1;
pub const ABS_PRESSURE: u16 = 0x18;
pub const ABS_TILT_X: u16 = 0x1a;
pub const ABS_TILT_Y: u16 = 0x1b;
pub const BTN_TOOL_RUBBER: u16 = 0x141;
pub const BTN_TOOL_BRUSH: u16 = 0x142;
pub const BTN_TOUCH: u16 = 0x14a;
pub const BTN_STYLUS: u16 = 0x14b;
const PRESSURE_LINEAR_TOOL: u16 = 0x152;
const PRESSURE_CURVE_TOOL: u16 = 0x153;

#[derive(Clone, Copy, Debug, Eq, PartialEq)]
enum ReaderType {
    Button,
    Draw,
    Erase,
    SideErase,
}

#[derive(Clone, Copy, Debug, PartialEq)]
pub struct TouchEvent {
    pub x: f32,
    pub y: f32,
    pub pressure: i32,
    pub tilt_x: i32,
    pub tilt_y: i32,
    pub is_erasing: bool,
    pub shortcut_drawing: bool,
    pub shortcut_erasing: bool,
    pub state: i32,
    pub timestamp_ms: i64,
}

#[derive(Clone, Copy, Debug, PartialEq)]
struct Rect {
    left: f32,
    top: f32,
    right: f32,
    bottom: f32,
}

impl Rect {
    fn new(a: f32, b: f32, c: f32, d: f32) -> Self {
        Self {
            left: a.min(c),
            top: b.min(d),
            right: a.max(c),
            bottom: b.max(d),
        }
    }

    fn contains_expanded(self, x: f32, y: f32, margin: f32) -> bool {
        x + margin >= self.left
            && x - margin <= self.right
            && y + margin >= self.top
            && y - margin <= self.bottom
    }
}

#[derive(Debug)]
pub struct PenManager {
    reader: ReaderType,
    reader_state: u32,
    pen_state: i32,
    max_pressure: f32,
    x: f32,
    y: f32,
    pressure: i32,
    smoothed_pressure: i32,
    tilt_x: i32,
    tilt_y: i32,
    timestamp_ms: i64,
    stroke_width: f32,
    region_mode: i32,
    last_region_hit: Option<usize>,
    limit_regions: Vec<Rect>,
    exclude_regions: Vec<Rect>,
    side_button_erase: bool,
    btn_touch: bool,
    btn_stylus: bool,
    btn_tool_rubber: bool,
    btn_tool_brush: bool,
    pressure_tool: u16,
}

impl Default for PenManager {
    fn default() -> Self {
        Self {
            reader: ReaderType::Button,
            reader_state: 0,
            pen_state: 4,
            max_pressure: 4096.0,
            x: 0.0,
            y: 0.0,
            pressure: 0,
            smoothed_pressure: -1,
            tilt_x: 0,
            tilt_y: 0,
            timestamp_ms: 0,
            stroke_width: 0.0,
            region_mode: 0,
            last_region_hit: None,
            limit_regions: Vec::new(),
            exclude_regions: Vec::new(),
            side_button_erase: true,
            btn_touch: false,
            btn_stylus: false,
            btn_tool_rubber: false,
            btn_tool_brush: false,
            pressure_tool: PRESSURE_LINEAR_TOOL,
        }
    }
}

impl PenManager {
    pub fn set_max_pressure(&mut self, value: f32) {
        if value > 0.0 {
            self.max_pressure = value;
        }
    }

    pub fn set_pen_state(&mut self, value: i32) {
        self.pen_state = value;
    }
    pub fn set_stroke_width(&mut self, value: f32) {
        self.stroke_width = value;
    }
    pub fn set_region_mode(&mut self, value: i32) {
        self.region_mode = value;
    }
    pub fn enable_side_button_erase(&mut self, value: bool) {
        self.side_button_erase = value;
    }

    pub fn set_limit_regions(&mut self, values: &[f32]) {
        self.limit_regions = values
            .chunks_exact(4)
            .take(1024)
            .map(|r| Rect::new(r[0], r[1], r[2], r[3]))
            .collect();
        self.last_region_hit = None;
    }

    pub fn set_exclude_regions(&mut self, values: &[f32]) {
        self.exclude_regions = values
            .chunks_exact(4)
            .take(1024)
            .map(|r| Rect::new(r[0], r[1], r[2], r[3]))
            .collect();
    }

    // Mirrors the reference library: the forced release fires only for pen
    // states below 2. The recovered Java surface only ever sets state 4, so a
    // stroke in progress deliberately survives a pause()/resume() cycle.
    pub fn pause(&mut self) -> Vec<TouchEvent> {
        if self.pen_state < 2 {
            vec![self.report(6)]
        } else {
            Vec::new()
        }
    }

    pub fn reset_reader(&mut self) -> Vec<TouchEvent> {
        let mut out = Vec::new();
        self.deactivate(&mut out);
        self.reader = ReaderType::Button;
        self.reader_state = 0;
        self.last_region_hit = None;
        out
    }

    pub fn process_input(
        &mut self,
        kind: u16,
        code: u16,
        value: i32,
        timestamp_ms: i64,
    ) -> Vec<TouchEvent> {
        self.timestamp_ms = timestamp_ms;
        match kind {
            EV_ABS => {
                match code {
                    ABS_X => self.x = value as f32,
                    ABS_Y => self.y = value as f32,
                    ABS_PRESSURE => self.pressure = value,
                    ABS_TILT_X => self.tilt_x = value,
                    ABS_TILT_Y => self.tilt_y = value,
                    _ => {}
                }
                Vec::new()
            }
            EV_KEY => {
                if code == PRESSURE_LINEAR_TOOL || code == PRESSURE_CURVE_TOOL {
                    if value > 0 {
                        self.pressure_tool = code;
                        if code == PRESSURE_LINEAR_TOOL {
                            self.smoothed_pressure = -1;
                        }
                    }
                    return Vec::new();
                }
                let down = value > 0;
                match code {
                    BTN_TOOL_RUBBER => self.btn_tool_rubber = down,
                    BTN_TOOL_BRUSH => self.btn_tool_brush = down,
                    BTN_TOUCH => self.btn_touch = down,
                    BTN_STYLUS => self.btn_stylus = down,
                    _ => {}
                }
                let mut out = Vec::new();
                match self.reader {
                    ReaderType::Erase if !self.rubber_down() => {
                        self.change_reader(ReaderType::Button, &mut out)
                    }
                    ReaderType::Draw | ReaderType::SideErase if !self.btn_touch => {
                        self.change_reader(ReaderType::Button, &mut out)
                    }
                    _ => {}
                }
                out
            }
            EV_SYN => {
                let mut out = Vec::new();
                if self.reader == ReaderType::Button && self.pressure > 0 {
                    let next = if self.rubber_down() {
                        Some(ReaderType::Erase)
                    } else if self.side_touch_down() {
                        Some(ReaderType::SideErase)
                    } else if self.btn_touch {
                        Some(ReaderType::Draw)
                    } else {
                        None
                    };
                    if let Some(next) = next {
                        self.change_reader(next, &mut out);
                    }
                } else if self.reader != ReaderType::Button {
                    self.on_point(&mut out);
                }
                out.push(self.report(5));
                out
            }
            _ => Vec::new(),
        }
    }

    fn rubber_down(&self) -> bool {
        self.btn_touch && self.btn_tool_rubber
    }
    fn side_touch_down(&self) -> bool {
        self.side_button_erase && self.btn_stylus && self.btn_touch
    }

    fn change_reader(&mut self, next: ReaderType, out: &mut Vec<TouchEvent>) {
        self.deactivate(out);
        self.last_region_hit = None;
        self.reader = next;
        self.reader_state = 0;
        if next != ReaderType::Button {
            self.on_point(out);
        }
    }

    fn deactivate(&mut self, out: &mut Vec<TouchEvent>) {
        if self.reader != ReaderType::Button && self.reader_state == 1 {
            out.push(self.report(2));
        }
        self.reader_state = 0;
    }

    fn on_point(&mut self, out: &mut Vec<TouchEvent>) {
        let valid = self.in_valid_region(self.x, self.y);
        if valid && (self.reader_state & !2) == 0 {
            out.push(self.report(0));
        } else if self.reader_state == 1 {
            out.push(self.report(if valid { 1 } else { 3 }));
        }
        self.reader_state = if valid { 1 } else { 2 };
    }

    fn in_valid_region(&mut self, x: f32, y: f32) -> bool {
        let margin = self.stroke_width * 0.5;
        let hit = self
            .limit_regions
            .iter()
            .position(|r| r.contains_expanded(x, y, margin));
        if !self.limit_regions.is_empty() && hit.is_none() {
            return false;
        }
        if self.region_mode != 0 {
            match (self.last_region_hit, hit) {
                (None, Some(index)) => self.last_region_hit = Some(index),
                (Some(previous), Some(index)) if self.region_mode == 1 && previous == index => {}
                (Some(_), Some(_)) => return false,
                _ => {}
            }
        }
        !self
            .exclude_regions
            .iter()
            .any(|r| r.contains_expanded(x, y, margin))
    }

    fn normalized_pressure(&mut self) -> i32 {
        if self.pressure_tool != PRESSURE_CURVE_TOOL {
            return self.pressure;
        }
        let previous = if self.smoothed_pressure < 0 {
            self.pressure
        } else {
            self.smoothed_pressure
        };
        self.smoothed_pressure = ((previous as f64 * 0.5 + self.pressure as f64) / 1.5) as i32;
        let ratio = self.smoothed_pressure as f64 / self.max_pressure as f64;
        let scaled = if ratio < 0.15 {
            ratio * 4.0
        } else {
            ((ratio - 0.135).powf(0.2) + 0.17).min(1.0)
        };
        (self.max_pressure as f64 * scaled) as i32
    }

    fn report(&mut self, state: i32) -> TouchEvent {
        TouchEvent {
            x: self.x,
            y: self.y,
            pressure: self.normalized_pressure(),
            tilt_x: self.tilt_x,
            tilt_y: self.tilt_y,
            is_erasing: matches!(self.reader, ReaderType::Erase | ReaderType::SideErase),
            shortcut_drawing: false,
            shortcut_erasing: self.reader == ReaderType::SideErase,
            state,
            timestamp_ms: self.timestamp_ms,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    fn feed(manager: &mut PenManager, kind: u16, code: u16, value: i32) -> Vec<TouchEvent> {
        manager.process_input(kind, code, value, 123)
    }

    #[test]
    fn draw_down_move_up_matches_recovered_states() {
        let mut p = PenManager::default();
        feed(&mut p, EV_KEY, BTN_TOUCH, 1);
        feed(&mut p, EV_ABS, ABS_PRESSURE, 100);
        let down = feed(&mut p, EV_SYN, 0, 0);
        assert_eq!(down.iter().map(|e| e.state).collect::<Vec<_>>(), vec![0, 5]);
        assert!(down.iter().all(|event| event.timestamp_ms == 123));
        let moved = feed(&mut p, EV_SYN, 0, 0);
        assert_eq!(
            moved.iter().map(|e| e.state).collect::<Vec<_>>(),
            vec![1, 5]
        );
        let up = feed(&mut p, EV_KEY, BTN_TOUCH, 0);
        assert_eq!(up.iter().map(|e| e.state).collect::<Vec<_>>(), vec![2]);
    }

    #[test]
    fn side_button_selects_shortcut_eraser() {
        let mut p = PenManager::default();
        feed(&mut p, EV_KEY, BTN_STYLUS, 1);
        feed(&mut p, EV_KEY, BTN_TOUCH, 1);
        feed(&mut p, EV_ABS, ABS_PRESSURE, 1);
        let events = feed(&mut p, EV_SYN, 0, 0);
        assert!(events[0].is_erasing && events[0].shortcut_erasing);
    }

    #[test]
    fn regions_are_normalized_excluded_and_sticky() {
        let mut p = PenManager::default();
        p.set_limit_regions(&[10.0, 10.0, 0.0, 0.0, 20.0, 20.0, 30.0, 30.0]);
        p.set_region_mode(1);
        assert!(p.in_valid_region(5.0, 5.0));
        assert!(!p.in_valid_region(25.0, 25.0));
        p.set_exclude_regions(&[3.0, 3.0, 7.0, 7.0]);
        assert!(!p.in_valid_region(5.0, 5.0));
    }

    #[test]
    fn curved_pressure_is_bounded() {
        let mut p = PenManager::default();
        feed(&mut p, EV_KEY, PRESSURE_CURVE_TOOL, 1);
        feed(&mut p, EV_ABS, ABS_PRESSURE, 4096);
        assert_eq!(p.normalized_pressure(), 4096);
    }
}

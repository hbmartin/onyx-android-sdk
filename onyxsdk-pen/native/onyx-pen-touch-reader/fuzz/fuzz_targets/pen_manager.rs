#![no_main]

use libfuzzer_sys::fuzz_target;
use onyx_pen_touch_reader::state::PenManager;

fn i32_from(bytes: [u8; 4]) -> i32 {
    i32::from_le_bytes(bytes)
}

fuzz_target!(|data: &[u8]| {
    let mut manager = PenManager::default();
    let mut timestamp = 0_i64;

    for command in data.chunks(14).take(4096) {
        let byte = |index: usize| command.get(index).copied().unwrap_or_default();
        let value = i32_from([byte(3), byte(4), byte(5), byte(6)]);
        timestamp = timestamp.saturating_add(u16::from_le_bytes([byte(7), byte(8)]) as i64);

        match byte(0) % 9 {
            0 => {
                let kind = [0_u16, 1, 3, u16::MAX][(byte(1) % 4) as usize];
                let code = u16::from_le_bytes([byte(2), byte(3)]);
                let _ = manager.process_input(kind, code, value, timestamp);
            }
            1 => manager.set_max_pressure(f32::from_bits(value as u32)),
            2 => manager.set_pen_state(value),
            3 => manager.set_stroke_width(f32::from_bits(value as u32)),
            4 => manager.set_region_mode(value),
            5 => manager.enable_side_button_erase(value & 1 != 0),
            6 => manager.set_limit_regions(&[
                f32::from_bits(value as u32),
                f32::from_bits(i32_from([byte(9), byte(10), byte(11), byte(12)]) as u32),
                byte(1) as f32,
                byte(2) as f32,
            ]),
            7 => manager.set_exclude_regions(&[
                value as f32,
                i32_from([byte(9), byte(10), byte(11), byte(12)]) as f32,
                -(byte(1) as f32),
                -(byte(2) as f32),
            ]),
            _ if byte(13) & 1 == 0 => {
                let _ = manager.pause();
            }
            _ => {
                let _ = manager.reset_reader();
            }
        }
    }
});

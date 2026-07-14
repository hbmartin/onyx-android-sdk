#![allow(non_snake_case)]
#![cfg_attr(not(any(target_os = "android", target_os = "linux")), allow(dead_code))]

pub mod state;

use std::sync::atomic::{AtomicBool, AtomicI32, AtomicI64, Ordering};
use std::sync::{Mutex, OnceLock};

use jni::objects::{JFloatArray, JObject, JValue};
use jni::sys::{jboolean, jfloat, jint};
use jni::{jni_sig, jni_str, Env, EnvUnowned};
use state::{PenManager, TouchEvent};

struct WakeFd {
    fd: Mutex<i32>,
}

impl WakeFd {
    fn new() -> Self {
        Self { fd: Mutex::new(-1) }
    }

    fn set(&self, fd: i32) {
        *self.fd.lock().unwrap() = fd;
    }

    fn with_fd<T>(&self, action: impl FnOnce(i32) -> T) -> Option<T> {
        let fd = self.fd.lock().unwrap();
        (*fd >= 0).then(|| action(*fd))
    }

    fn take(&self) -> i32 {
        std::mem::replace(&mut *self.fd.lock().unwrap(), -1)
    }
}

struct Runtime {
    manager: Mutex<PenManager>,
    running: AtomicBool,
    input_fd: AtomicI32,
    wake_fd: WakeFd,
    debug: AtomicBool,
    cancelled_through: AtomicI64,
}

fn runtime() -> &'static Runtime {
    static RUNTIME: OnceLock<Runtime> = OnceLock::new();
    RUNTIME.get_or_init(|| Runtime {
        manager: Mutex::new(PenManager::default()),
        running: AtomicBool::new(false),
        input_fd: AtomicI32::new(-1),
        wake_fd: WakeFd::new(),
        debug: AtomicBool::new(false),
        cancelled_through: AtomicI64::new(0),
    })
}

fn read_float_array(env: &mut Env, array: JFloatArray) -> Vec<f32> {
    let Ok(length) = array.len(env) else {
        return Vec::new();
    };
    let mut values = vec![0.0; length];
    if array.get_region(env, 0, &mut values).is_ok() {
        values
    } else {
        Vec::new()
    }
}

fn emit(env: &mut Env, object: &JObject, event: TouchEvent) {
    let args = [
        JValue::Float(event.x),
        JValue::Float(event.y),
        JValue::Int(event.pressure),
        JValue::Int(event.tilt_x),
        JValue::Int(event.tilt_y),
        JValue::Bool(event.is_erasing),
        JValue::Bool(event.shortcut_drawing),
        JValue::Bool(event.shortcut_erasing),
        JValue::Int(event.state),
        JValue::Long(event.timestamp_nanos),
    ];
    let _ = env.call_method(
        object,
        jni_str!("onTouchPointReceived"),
        jni_sig!("(FFIIIZZZIJ)V"),
        &args,
    );
}

#[cfg(any(target_os = "android", target_os = "linux"))]
#[derive(Clone, Copy, Debug, Default)]
struct AbsInfo {
    minimum: i32,
    maximum: i32,
    fuzz: i32,
    flat: i32,
    resolution: i32,
}

#[cfg(any(target_os = "android", target_os = "linux"))]
#[derive(Clone, Copy, Debug, Default)]
struct InputDeviceInfo {
    x: AbsInfo,
    y: AbsInfo,
    pressure: AbsInfo,
    tilt_x: AbsInfo,
    tilt_y: AbsInfo,
    kernel_monotonic_clock: bool,
    timestamp_offset_nanos: i64,
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn emit_device_info(env: &mut Env, object: &JObject, info: InputDeviceInfo) {
    let axes = [info.x, info.y, info.pressure, info.tilt_x, info.tilt_y];
    let mut args = Vec::with_capacity(26);
    for axis in axes {
        args.push(JValue::Int(axis.minimum));
        args.push(JValue::Int(axis.maximum));
        args.push(JValue::Int(axis.fuzz));
        args.push(JValue::Int(axis.flat));
        args.push(JValue::Int(axis.resolution));
    }
    args.push(JValue::Bool(info.kernel_monotonic_clock));
    let _ = env.call_method(
        object,
        jni_str!("onRawInputDeviceInfo"),
        jni_sig!("(IIIIIIIIIIIIIIIIIIIIIIIIIZ)V"),
        &args,
    );
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeDebug(
    _env: EnvUnowned,
    _object: JObject,
    enabled: jboolean,
) {
    runtime().debug.store(enabled, Ordering::SeqCst);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeIsValid(
    _env: EnvUnowned,
    _object: JObject,
) -> jboolean {
    #[cfg(any(target_os = "android", target_os = "linux"))]
    {
        let fd = runtime().input_fd.load(Ordering::SeqCst);
        if fd < 1 {
            return false;
        }
        let mut descriptor = libc::pollfd {
            fd,
            events: libc::POLLIN,
            revents: 0,
        };
        let result = unsafe { libc::poll(&mut descriptor, 1, 0) };
        result >= 0 && descriptor.revents & (libc::POLLHUP | libc::POLLNVAL) == 0
    }
    #[cfg(not(any(target_os = "android", target_os = "linux")))]
    false
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawClose(
    _env: EnvUnowned,
    _object: JObject,
    session: i64,
) {
    let rt = runtime();
    rt.cancelled_through.fetch_max(session, Ordering::SeqCst);
    rt.running.store(false, Ordering::SeqCst);
    if rt.debug.load(Ordering::SeqCst) {
        eprintln!("[onyx-touch-reader] close session={session}");
    }
    #[cfg(any(target_os = "android", target_os = "linux"))]
    {
        rt.wake_fd.with_fd(|fd| unsafe {
            let one: u64 = 1;
            libc::write(fd, &one as *const u64 as *const _, 8);
        });
    }
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetStrokeWidth(
    _env: EnvUnowned,
    _object: JObject,
    value: jfloat,
) {
    runtime().manager.lock().unwrap().set_stroke_width(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetLimitRegion<'local>(
    mut unowned_env: EnvUnowned<'local>,
    _object: JObject<'local>,
    array: JFloatArray<'local>,
) {
    unowned_env
        .with_env(|env| -> jni::errors::Result<()> {
            runtime()
                .manager
                .lock()
                .unwrap()
                .set_limit_regions(&read_float_array(env, array));
            Ok(())
        })
        .resolve::<jni::errors::LogErrorAndDefault>()
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetExcludeRegion<
    'local,
>(
    mut unowned_env: EnvUnowned<'local>,
    _object: JObject<'local>,
    array: JFloatArray<'local>,
) {
    unowned_env
        .with_env(|env| -> jni::errors::Result<()> {
            runtime()
                .manager
                .lock()
                .unwrap()
                .set_exclude_regions(&read_float_array(env, array));
            Ok(())
        })
        .resolve::<jni::errors::LogErrorAndDefault>()
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetPenState(
    _env: EnvUnowned,
    _object: JObject,
    value: jint,
) {
    runtime().manager.lock().unwrap().set_pen_state(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativePausePen<'local>(
    mut unowned_env: EnvUnowned<'local>,
    object: JObject<'local>,
) {
    unowned_env
        .with_env(|env| -> jni::errors::Result<()> {
            let events = runtime().manager.lock().unwrap().pause();
            for event in events {
                emit(env, &object, event);
            }
            Ok(())
        })
        .resolve::<jni::errors::LogErrorAndDefault>()
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetRegionMode(
    _env: EnvUnowned,
    _object: JObject,
    value: jint,
) {
    runtime().manager.lock().unwrap().set_region_mode(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeEnableSideBtnErase(
    _env: EnvUnowned,
    _object: JObject,
    enabled: jboolean,
) {
    runtime()
        .manager
        .lock()
        .unwrap()
        .enable_side_button_erase(enabled);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawReader<'local>(
    mut unowned_env: EnvUnowned<'local>,
    object: JObject<'local>,
    session: i64,
) {
    #[cfg(any(target_os = "android", target_os = "linux"))]
    unowned_env
        .with_env(|env| -> jni::errors::Result<()> {
            reader_loop(env, &object, session);
            Ok(())
        })
        .resolve::<jni::errors::LogErrorAndDefault>();
    #[cfg(not(any(target_os = "android", target_os = "linux")))]
    {
        let _ = (&mut unowned_env, &object, session);
    }
}

#[cfg(any(target_os = "android", target_os = "linux"))]
#[repr(C)]
struct InputEvent {
    sec: libc::c_long,
    usec: libc::c_long,
    kind: u16,
    code: u16,
    value: i32,
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn clock_nanos(clock_id: libc::clockid_t) -> i64 {
    let mut value = libc::timespec {
        tv_sec: 0,
        tv_nsec: 0,
    };
    if unsafe { libc::clock_gettime(clock_id, &mut value) } != 0 {
        return 0;
    }
    (value.tv_sec as i128 * 1_000_000_000i128 + value.tv_nsec as i128)
        .clamp(i64::MIN as i128, i64::MAX as i128) as i64
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn query_abs_info(fd: i32, code: u16) -> AbsInfo {
    let mut values = [0i32; 6];
    let request = 0x8018_4540u64 | code as u64;
    if unsafe { libc::ioctl(fd, request as _, values.as_mut_ptr()) } != 0 {
        return AbsInfo::default();
    }
    AbsInfo {
        minimum: values[1],
        maximum: values[2],
        fuzz: values[3],
        flat: values[4],
        resolution: values[5],
    }
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn configure_event_clock(fd: i32) -> (bool, i64) {
    let clock_id: libc::c_int = libc::CLOCK_MONOTONIC;
    let applied = unsafe { libc::ioctl(fd, 0x4004_45a0u64 as _, &clock_id) } == 0;
    if applied {
        (true, 0)
    } else {
        // Preserve the kernel event timestamp while translating its realtime clock domain
        // into CLOCK_MONOTONIC when EVIOCSCLOCKID is denied by the firmware.
        (
            false,
            clock_nanos(libc::CLOCK_MONOTONIC).saturating_sub(clock_nanos(libc::CLOCK_REALTIME)),
        )
    }
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn open_pen_device() -> Option<(i32, InputDeviceInfo)> {
    use std::ffi::{CStr, CString};
    for index in 0..16 {
        let path = CString::new(format!("/dev/input/event{index}")).unwrap();
        let fd = unsafe { libc::open(path.as_ptr(), libc::O_RDONLY | libc::O_CLOEXEC) };
        if fd < 0 {
            continue;
        }
        let mut name = [0 as libc::c_char; 80];
        let request = 0x8000_0000u64 | ((79u64) << 16) | ((b'E' as u64) << 8) | 0x06;
        let ok = unsafe { libc::ioctl(fd, request as _, name.as_mut_ptr()) } >= 0;
        let lower = if ok {
            unsafe { CStr::from_ptr(name.as_ptr()) }
                .to_string_lossy()
                .to_lowercase()
        } else {
            String::new()
        };
        if lower.contains("hanvon") || lower.contains("wacom") || lower.contains("onyx_emp") {
            let (kernel_monotonic_clock, timestamp_offset_nanos) = configure_event_clock(fd);
            return Some((
                fd,
                InputDeviceInfo {
                    x: query_abs_info(fd, state::ABS_X),
                    y: query_abs_info(fd, state::ABS_Y),
                    pressure: query_abs_info(fd, state::ABS_PRESSURE),
                    tilt_x: query_abs_info(fd, state::ABS_TILT_X),
                    tilt_y: query_abs_info(fd, state::ABS_TILT_Y),
                    kernel_monotonic_clock,
                    timestamp_offset_nanos,
                },
            ));
        }
        unsafe {
            libc::close(fd);
        }
    }
    None
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn reader_loop(env: &mut Env, object: &JObject, session: i64) {
    let rt = runtime();
    if session <= rt.cancelled_through.load(Ordering::SeqCst) {
        if rt.debug.load(Ordering::SeqCst) {
            eprintln!("[onyx-touch-reader] skip cancelled session={session}");
        }
        return;
    }
    let Some((input_fd, device_info)) = open_pen_device() else {
        if rt.debug.load(Ordering::SeqCst) {
            eprintln!("[onyx-touch-reader] no pen device session={session}");
        }
        return;
    };
    let wake_fd = unsafe { libc::eventfd(0, libc::EFD_CLOEXEC) };
    if wake_fd < 0 {
        unsafe {
            libc::close(input_fd);
        }
        return;
    }
    rt.input_fd.store(input_fd, Ordering::SeqCst);
    rt.wake_fd.set(wake_fd);
    if session <= rt.cancelled_through.load(Ordering::SeqCst) {
        let input_fd = rt.input_fd.swap(-1, Ordering::SeqCst);
        let wake_fd = rt.wake_fd.take();
        unsafe {
            if input_fd >= 0 {
                libc::close(input_fd);
            }
            if wake_fd >= 0 {
                libc::close(wake_fd);
            }
        }
        if rt.debug.load(Ordering::SeqCst) {
            eprintln!("[onyx-touch-reader] cancelled during open session={session}");
        }
        return;
    }
    rt.running.store(true, Ordering::SeqCst);
    if rt.debug.load(Ordering::SeqCst) {
        eprintln!(
            "[onyx-touch-reader] opened session={session} input_fd={input_fd} wake_fd={wake_fd} max_pressure={} monotonic_clock={}"
            , device_info.pressure.maximum, device_info.kernel_monotonic_clock
        );
    }
    emit_device_info(env, object, device_info);
    {
        let mut manager = rt.manager.lock().unwrap();
        manager.set_max_pressure(if device_info.pressure.maximum > 0 {
            device_info.pressure.maximum as f32
        } else {
            4096.0
        });
        manager.reset_reader();
    }
    let mut pollfds = [
        libc::pollfd {
            fd: input_fd,
            events: libc::POLLIN,
            revents: 0,
        },
        libc::pollfd {
            fd: wake_fd,
            events: libc::POLLIN,
            revents: 0,
        },
    ];
    while rt.running.load(Ordering::SeqCst) {
        let ready = unsafe { libc::poll(pollfds.as_mut_ptr(), pollfds.len() as _, -1) };
        if ready <= 0 || pollfds[1].revents & libc::POLLIN != 0 {
            break;
        }
        if pollfds[0].revents & libc::POLLIN != 0 {
            let mut event = InputEvent {
                sec: 0,
                usec: 0,
                kind: 0,
                code: 0,
                value: 0,
            };
            let read = unsafe {
                libc::read(
                    input_fd,
                    &mut event as *mut InputEvent as *mut _,
                    std::mem::size_of::<InputEvent>(),
                )
            };
            if read as usize == std::mem::size_of::<InputEvent>() {
                let raw_timestamp =
                    (event.sec as i128 * 1_000_000_000i128 + event.usec as i128 * 1_000i128)
                        .clamp(i64::MIN as i128, i64::MAX as i128) as i64;
                let timestamp_nanos =
                    raw_timestamp.saturating_add(device_info.timestamp_offset_nanos);
                let events = rt.manager.lock().unwrap().process_input(
                    event.kind,
                    event.code,
                    event.value,
                    timestamp_nanos,
                );
                for event in events {
                    emit(env, object, event);
                }
            }
        }
    }
    rt.running.store(false, Ordering::SeqCst);
    let input_fd = rt.input_fd.swap(-1, Ordering::SeqCst);
    let wake_fd = rt.wake_fd.take();
    unsafe {
        if input_fd >= 0 {
            libc::close(input_fd);
        }
        if wake_fd >= 0 {
            libc::close(wake_fd);
        }
    }
    if rt.debug.load(Ordering::SeqCst) {
        eprintln!("[onyx-touch-reader] exited session={session}");
    }
}

#[cfg(test)]
mod tests {
    use super::WakeFd;
    use std::sync::{mpsc, Arc};
    use std::time::Duration;

    #[test]
    fn wake_action_and_descriptor_take_are_mutually_exclusive() {
        let wake_fd = Arc::new(WakeFd::new());
        wake_fd.set(41);
        let (action_entered_tx, action_entered_rx) = mpsc::channel();
        let (release_action_tx, release_action_rx) = mpsc::channel();
        let action_fd = Arc::clone(&wake_fd);
        let action = std::thread::spawn(move || {
            assert_eq!(
                action_fd.with_fd(|fd| {
                    assert_eq!(fd, 41);
                    action_entered_tx.send(()).unwrap();
                    release_action_rx.recv().unwrap();
                }),
                Some(())
            );
        });
        action_entered_rx
            .recv_timeout(Duration::from_secs(1))
            .unwrap();

        let (take_started_tx, take_started_rx) = mpsc::channel();
        let (take_finished_tx, take_finished_rx) = mpsc::channel();
        let take_fd = Arc::clone(&wake_fd);
        let take = std::thread::spawn(move || {
            take_started_tx.send(()).unwrap();
            take_finished_tx.send(take_fd.take()).unwrap();
        });
        take_started_rx
            .recv_timeout(Duration::from_secs(1))
            .unwrap();
        assert!(matches!(
            take_finished_rx.recv_timeout(Duration::from_millis(100)),
            Err(mpsc::RecvTimeoutError::Timeout)
        ));

        release_action_tx.send(()).unwrap();
        action.join().unwrap();
        assert_eq!(
            take_finished_rx
                .recv_timeout(Duration::from_secs(1))
                .unwrap(),
            41
        );
        take.join().unwrap();
    }

    #[test]
    fn wake_action_is_skipped_after_descriptor_is_taken() {
        let wake_fd = WakeFd::new();
        wake_fd.set(19);

        assert_eq!(wake_fd.take(), 19);
        assert_eq!(
            wake_fd.with_fd(|_| panic!("closed descriptor reused")),
            None
        );
    }
}

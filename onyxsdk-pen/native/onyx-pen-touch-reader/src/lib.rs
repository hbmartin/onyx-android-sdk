#![allow(non_snake_case)]
#![cfg_attr(not(any(target_os = "android", target_os = "linux")), allow(dead_code))]

mod state;

use std::sync::atomic::{AtomicBool, AtomicI32, Ordering};
use std::sync::{Mutex, OnceLock};

use jni::objects::{JFloatArray, JObject, JValue};
use jni::sys::{jboolean, jfloat, jint};
use jni::JNIEnv;
use state::{PenManager, TouchEvent};

struct Runtime {
    manager: Mutex<PenManager>,
    running: AtomicBool,
    input_fd: AtomicI32,
    wake_fd: AtomicI32,
    debug: AtomicBool,
}

fn runtime() -> &'static Runtime {
    static RUNTIME: OnceLock<Runtime> = OnceLock::new();
    RUNTIME.get_or_init(|| Runtime {
        manager: Mutex::new(PenManager::default()),
        running: AtomicBool::new(false),
        input_fd: AtomicI32::new(-1),
        wake_fd: AtomicI32::new(-1),
        debug: AtomicBool::new(false),
    })
}

fn read_float_array(env: &mut JNIEnv, array: JFloatArray) -> Vec<f32> {
    let Ok(length) = env.get_array_length(&array) else {
        return Vec::new();
    };
    let mut values = vec![0.0; length as usize];
    if env.get_float_array_region(&array, 0, &mut values).is_ok() {
        values
    } else {
        Vec::new()
    }
}

fn emit(env: &mut JNIEnv, object: &JObject, event: TouchEvent) {
    let args = [
        JValue::Float(event.x),
        JValue::Float(event.y),
        JValue::Int(event.pressure),
        JValue::Int(event.tilt_x),
        JValue::Int(event.tilt_y),
        JValue::Bool(event.is_erasing as jboolean),
        JValue::Bool(event.shortcut_drawing as jboolean),
        JValue::Bool(event.shortcut_erasing as jboolean),
        JValue::Int(event.state),
        JValue::Long(event.timestamp_us),
    ];
    let _ = env.call_method(object, "onTouchPointReceived", "(FFIIIZZZIJ)V", &args);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeDebug(
    _env: JNIEnv,
    _object: JObject,
    enabled: jboolean,
) {
    runtime().debug.store(enabled != 0, Ordering::SeqCst);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeIsValid(
    _env: JNIEnv,
    _object: JObject,
) -> jboolean {
    #[cfg(any(target_os = "android", target_os = "linux"))]
    {
        let fd = runtime().input_fd.load(Ordering::SeqCst);
        if fd < 1 {
            return 0;
        }
        let mut descriptor = libc::pollfd {
            fd,
            events: libc::POLLIN,
            revents: 0,
        };
        let result = unsafe { libc::poll(&mut descriptor, 1, 0) };
        (result >= 0 && descriptor.revents & (libc::POLLHUP | libc::POLLNVAL) == 0) as jboolean
    }
    #[cfg(not(any(target_os = "android", target_os = "linux")))]
    0
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawClose(
    _env: JNIEnv,
    _object: JObject,
) {
    let rt = runtime();
    rt.running.store(false, Ordering::SeqCst);
    #[cfg(any(target_os = "android", target_os = "linux"))]
    unsafe {
        let fd = rt.wake_fd.load(Ordering::SeqCst);
        if fd >= 0 {
            let one: u64 = 1;
            libc::write(fd, &one as *const u64 as *const _, 8);
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetStrokeWidth(
    _env: JNIEnv,
    _object: JObject,
    value: jfloat,
) {
    runtime().manager.lock().unwrap().set_stroke_width(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetLimitRegion(
    mut env: JNIEnv,
    _object: JObject,
    array: JFloatArray,
) {
    runtime()
        .manager
        .lock()
        .unwrap()
        .set_limit_regions(&read_float_array(&mut env, array));
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetExcludeRegion(
    mut env: JNIEnv,
    _object: JObject,
    array: JFloatArray,
) {
    runtime()
        .manager
        .lock()
        .unwrap()
        .set_exclude_regions(&read_float_array(&mut env, array));
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetPenState(
    _env: JNIEnv,
    _object: JObject,
    value: jint,
) {
    runtime().manager.lock().unwrap().set_pen_state(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativePausePen(
    mut env: JNIEnv,
    object: JObject,
) {
    let events = runtime().manager.lock().unwrap().pause();
    for event in events {
        emit(&mut env, &object, event);
    }
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetRegionMode(
    _env: JNIEnv,
    _object: JObject,
    value: jint,
) {
    runtime().manager.lock().unwrap().set_region_mode(value);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeEnableSideBtnErase(
    _env: JNIEnv,
    _object: JObject,
    enabled: jboolean,
) {
    runtime()
        .manager
        .lock()
        .unwrap()
        .enable_side_button_erase(enabled != 0);
}

#[no_mangle]
pub extern "system" fn Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawReader(
    mut env: JNIEnv,
    object: JObject,
) {
    #[cfg(any(target_os = "android", target_os = "linux"))]
    reader_loop(&mut env, &object);
    #[cfg(not(any(target_os = "android", target_os = "linux")))]
    {
        let _ = (&mut env, &object);
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
fn now_us() -> i64 {
    use std::time::{SystemTime, UNIX_EPOCH};
    SystemTime::now()
        .duration_since(UNIX_EPOCH)
        .unwrap_or_default()
        .as_micros() as i64
}

#[cfg(any(target_os = "android", target_os = "linux"))]
fn open_pen_device() -> Option<(i32, f32)> {
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
            let mut abs = [0i32; 6];
            let rc = unsafe { libc::ioctl(fd, 0x8018_4558u64 as _, abs.as_mut_ptr()) };
            return Some((
                fd,
                if rc == 0 && abs[2] > 0 {
                    abs[2] as f32
                } else {
                    4096.0
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
fn reader_loop(env: &mut JNIEnv, object: &JObject) {
    let rt = runtime();
    let Some((input_fd, max_pressure)) = open_pen_device() else {
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
    rt.wake_fd.store(wake_fd, Ordering::SeqCst);
    rt.running.store(true, Ordering::SeqCst);
    {
        let mut manager = rt.manager.lock().unwrap();
        manager.set_max_pressure(max_pressure);
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
                let events = rt.manager.lock().unwrap().process_input(
                    event.kind,
                    event.code,
                    event.value,
                    now_us(),
                );
                for event in events {
                    emit(env, object, event);
                }
            }
        }
    }
    rt.running.store(false, Ordering::SeqCst);
    unsafe {
        libc::close(input_fd);
        libc::close(wake_fd);
    }
    rt.input_fd.store(-1, Ordering::SeqCst);
    rt.wake_fd.store(-1, Ordering::SeqCst);
}

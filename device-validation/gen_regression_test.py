#!/usr/bin/env python3
"""Generates the base-module API-surface regression test from the pre-repair
classified diff plus the reference surface.

usage: gen_regression_test.py <pre-repair-classified.json> <reference-classes.jar>

For every class the classifier flagged, emits assertions that pin the repaired
state: previously-missing members exist with reference modifiers, previously
extra surface is gone, flag/visibility repairs hold, generic signatures appear
in the class file, and kotlin.Metadata matches the reference hash. The
classified JSON that drove the original repairs is what selects the pinned
members, so re-running against an already-repaired build reproduces the same
test.
"""

import hashlib
import json
import re
import sys
from pathlib import Path

import classify_api_differences as cad
import dump_metadata as dm

REPO = Path(__file__).resolve().parent.parent
CLASSIFIED = Path(sys.argv[1])
REFERENCE_JAR = Path(sys.argv[2])
OUTPUT = REPO / "onyxsdk-base/src/test/java/com/onyx/android/sdk/RecoveredApiSurfaceRegressionTest.java"

FLAG_TO_MODIFIER = {
    "ACC_PUBLIC": "Modifier.PUBLIC",
    "ACC_PROTECTED": "Modifier.PROTECTED",
    "ACC_PRIVATE": "Modifier.PRIVATE",
    "ACC_STATIC": "Modifier.STATIC",
    "ACC_FINAL": "Modifier.FINAL",
    "ACC_ABSTRACT": "Modifier.ABSTRACT",
}
CHECKED_FLAGS = ["ACC_PUBLIC", "ACC_PROTECTED", "ACC_PRIVATE", "ACC_STATIC",
                 "ACC_FINAL", "ACC_ABSTRACT"]


def modifier_expr(flags):
    parts = [FLAG_TO_MODIFIER[f] for f in CHECKED_FLAGS if f in flags]
    return " | ".join(parts) if parts else "0"


def batch_of(class_name):
    if ".extension." in class_name or class_name.endswith("Kt"):
        return "kotlinFacades"
    if ".wifi." in class_name:
        return "wifi"
    if ".rx." in class_name:
        return "rxScheduler"
    if ".calendar." in class_name:
        return "service"
    if ".common." in class_name:
        return "commonCollections"
    if ".data." in class_name or ".reader." in class_name:
        return "dataModels"
    return "utilities"


def metadata_hashes(class_name):
    entry = class_name.replace(".", "/") + ".class"
    import zipfile
    with zipfile.ZipFile(REFERENCE_JAR) as archive:
        data = archive.read(entry)
    import struct
    pool_count = struct.unpack_from(">H", data, 8)[0]
    pool, offset = dm.parse_constant_pool(data, 10, pool_count)
    offset += 6
    interfaces = struct.unpack_from(">H", data, offset)[0]
    offset += 2 + interfaces * 2
    for _ in ("fields", "methods"):
        count = struct.unpack_from(">H", data, offset)[0]
        offset += 2
        for _ in range(count):
            offset += 6
            attr_count = struct.unpack_from(">H", data, offset)[0]
            offset += 2
            for _ in range(attr_count):
                length = struct.unpack_from(">I", data, offset + 2)[0]
                offset += 6 + length
    attr_count = struct.unpack_from(">H", data, offset)[0]
    offset += 2
    for _ in range(attr_count):
        name_index = struct.unpack_from(">H", data, offset)[0]
        length = struct.unpack_from(">I", data, offset + 2)[0]
        attr_name = dm.modified_utf8(pool[name_index][1])
        body = offset + 6
        if attr_name == "RuntimeVisibleAnnotations":
            annotation_count = struct.unpack_from(">H", data, body)[0]
            cursor = body + 2
            for _ in range(annotation_count):
                (annotation_type, pairs), cursor = dm.read_annotation(data, cursor, pool)
                if annotation_type == "Lkotlin/Metadata;":
                    elements = dict(pairs)

                    def strings(tag_value):
                        return [v for _, v in tag_value[1]]

                    d1 = strings(elements["d1"]) if "d1" in elements else []
                    d2 = strings(elements["d2"]) if "d2" in elements else []
                    joined1 = " ".join(d1).encode("utf-8", "surrogatepass")
                    joined2 = " ".join(d2).encode("utf-8", "surrogatepass")
                    return (hashlib.sha256(joined1).hexdigest(),
                            hashlib.sha256(joined2).hexdigest())
        offset += 6 + length
    return None


# javac-inexpressible Kotlin compiler shapes (see RECOVERY_NOTES.md): these
# reference facts cannot hold in the rebuilt classes, so they are not pinned.
def excluded(class_name, message):
    if class_name == "com.onyx.android.sdk.rx.RxBaseBenchmarkRequest":
        return "execute" in message
    if class_name == "com.onyx.android.sdk.utils.ElementCounter":
        return ("put (Ljava/lang/Object;Ljava/lang/Object;)" in message
                or message.startswith("generic signature"))
    return False


def main():
    classified = json.loads(CLASSIFIED.read_text())["classes"]
    reference = cad.load_surface([REFERENCE_JAR])

    batches = {}

    def emit(class_name, line):
        batches.setdefault(batch_of(class_name), []).append("        " + line)

    for class_name in sorted(classified):
        data = classified[class_name]
        buckets = data["buckets"]
        ref = reference.get(class_name)
        if ref is None and data["classification"] != "binary_breaking":
            continue
        all_messages = [(bucket, message)
                        for bucket, messages in buckets.items()
                        for message in messages]
        emitted_class_comment = False

        def comment_once():
            nonlocal emitted_class_comment
            if not emitted_class_comment:
                emit(class_name, f"// {class_name}")
                emitted_class_comment = True

        if any(m == "class missing from candidate" for _, m in all_messages):
            comment_once()
            emit(class_name, f'Class<?> missing = load("{class_name}");')
            for member in ref.members.values():
                if not member.visible:
                    continue
                if member.kind == "method" and member.name not in ("<init>", "<clinit>"):
                    emit(class_name,
                         f'assertMethod(missing, "{member.name}", '
                         f'"{member.descriptor}", {modifier_expr(member.flags)});')
            continue

        for bucket, message in all_messages:
            if excluded(class_name, message):
                continue
            match = re.match(
                r"(?:missing member|missing Kotlin \$default bridge|missing bridge method|"
                r"missing synthetic accessor): "
                r"(?:public|protected|internal) (method|field) (\S+) (\S+)", message)
            if match:
                kind, name, descriptor = match.groups()
                member = ref.members.get((kind, name, descriptor))
                mods = modifier_expr(member.flags) if member else "Modifier.PUBLIC"
                comment_once()
                if kind == "field":
                    emit(class_name,
                         f'assertField("{class_name}", "{name}", "{descriptor}", {mods});')
                elif name == "<init>":
                    emit(class_name,
                         f'assertConstructor("{class_name}", "{descriptor}", {mods});')
                else:
                    emit(class_name,
                         f'assertMethod("{class_name}", "{name}", "{descriptor}", {mods});')
                continue
            match = re.match(
                r"(?:member flags changed|visibility narrowed): "
                r"(?:public|protected|internal) (method|field) (\S+) (\S+)", message)
            if match:
                kind, name, descriptor = match.groups()
                member = ref.members.get((kind, name, descriptor))
                if member is None:
                    continue
                comment_once()
                target = "Field" if kind == "field" else (
                    "Constructor" if name == "<init>" else "Method")
                if target == "Method":
                    emit(class_name,
                         f'assertMethod("{class_name}", "{name}", "{descriptor}", '
                         f'{modifier_expr(member.flags)});')
                elif target == "Field":
                    emit(class_name,
                         f'assertField("{class_name}", "{name}", "{descriptor}", '
                         f'{modifier_expr(member.flags)});')
                else:
                    emit(class_name,
                         f'assertConstructor("{class_name}", "{descriptor}", '
                         f'{modifier_expr(member.flags)});')
                continue
            match = re.match(
                r"(?:extra member|visibility widened): "
                r"(?:public|protected) (method|field) (\S+) (\S+)", message)
            if match:
                kind, name, descriptor = match.groups()
                ref_member = ref.members.get((kind, name, descriptor))
                comment_once()
                if ref_member is not None:
                    mods = modifier_expr(ref_member.flags)
                    if kind == "field":
                        emit(class_name,
                             f'assertField("{class_name}", "{name}", "{descriptor}", {mods});')
                    elif name == "<init>":
                        emit(class_name,
                             f'assertConstructor("{class_name}", "{descriptor}", {mods});')
                    else:
                        emit(class_name,
                             f'assertMethod("{class_name}", "{name}", "{descriptor}", {mods});')
                else:
                    if kind == "field":
                        emit(class_name,
                             f'assertNoVisibleField("{class_name}", "{name}", "{descriptor}");')
                    elif name == "<init>":
                        emit(class_name,
                             f'assertNoVisibleConstructor("{class_name}", "{descriptor}");')
                    else:
                        emit(class_name,
                             f'assertNoVisibleMethod("{class_name}", "{name}", "{descriptor}");')
                continue
            if message.startswith("unintended public default constructor"):
                comment_once()
                emit(class_name, f'assertNoVisibleConstructor("{class_name}", "()V");')
                continue
            if message.startswith("class flags"):
                comment_once()
                emit(class_name,
                     f'assertClassModifiers("{class_name}", {modifier_expr(ref.flags)});')
                continue
            match = re.search(r"generic signature: .*? '(?P<ref>[^']*)' -> ", message)
            if match and match.group("ref") != "None":
                comment_once()
                emit(class_name,
                     f'assertClassfileContains("{class_name}", '
                     f'"{java_escape(match.group("ref"))}");')
                continue
            if message == "kotlin.Metadata annotation differs":
                hashes = metadata_hashes(class_name)
                if hashes:
                    comment_once()
                    emit(class_name,
                         f'assertMetadataHashes("{class_name}", "{hashes[0]}", "{hashes[1]}");')

    body = []
    for batch in sorted(batches):
        body.append(f"    @Test\n    public void {batch}() throws Exception {{")
        body.extend(batches[batch])
        body.append("    }\n")
    OUTPUT.write_text(TEMPLATE.replace("//BODY//", "\n".join(body)))
    print(f"wrote {OUTPUT}")


TEMPLATE = """package com.onyx.android.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Pins the repaired JVM surface of the recovered base module: Kotlin
 * {@code $default} bridges, companion fields, synthetic constructors and
 * accessors, access flags, generic signatures, and kotlin.Metadata that were
 * restored to match the reference SDK bytecode. Generated from the classified
 * reference audit; regenerate with device-validation/classify_api_differences.py
 * if the reference surface ever changes.
 */
public class RecoveredApiSurfaceRegressionTest {

//BODY//

    private static final int VISIBILITY = Modifier.PUBLIC | Modifier.PROTECTED
            | Modifier.PRIVATE;

    private static Class<?> load(String name) throws Exception {
        return Class.forName(name);
    }

    private static Class<?>[] parameterTypes(String descriptor) throws Exception {
        List<Class<?>> types = new ArrayList<>();
        int[] index = {1};
        while (descriptor.charAt(index[0]) != ')') {
            types.add(descriptorType(descriptor, index));
        }
        return types.toArray(new Class<?>[0]);
    }

    private static Class<?> returnType(String descriptor) throws Exception {
        int[] index = {descriptor.indexOf(')') + 1};
        return descriptorType(descriptor, index);
    }

    private static Class<?> fieldType(String descriptor) throws Exception {
        int[] index = {0};
        Class<?> type = descriptorType(descriptor, index);
        assertEquals("complete field descriptor", descriptor.length(), index[0]);
        return type;
    }

    private static Class<?> descriptorType(String descriptor, int[] index) throws Exception {
        int dimensions = 0;
        while (descriptor.charAt(index[0]) == '[') {
            dimensions++;
            index[0]++;
        }
        char tag = descriptor.charAt(index[0]);
        String binaryName;
        if (tag == 'L') {
            int end = descriptor.indexOf(';', index[0]);
            binaryName = descriptor.substring(index[0] + 1, end).replace('/', '.');
            index[0] = end + 1;
        } else {
            binaryName = String.valueOf(tag);
            index[0]++;
        }
        Class<?> type = primitive(binaryName);
        if (type == null) {
            type = Class.forName(binaryName);
        }
        for (int i = 0; i < dimensions; i++) {
            type = java.lang.reflect.Array.newInstance(type, 0).getClass();
        }
        return type;
    }

    private static Class<?> primitive(String tag) {
        switch (tag) {
            case "Z": return boolean.class;
            case "B": return byte.class;
            case "C": return char.class;
            case "S": return short.class;
            case "I": return int.class;
            case "J": return long.class;
            case "F": return float.class;
            case "D": return double.class;
            case "V": return void.class;
            default: return null;
        }
    }

    private static void assertModifiers(String what, int expected, int actual) {
        int checked = VISIBILITY | Modifier.STATIC | Modifier.FINAL | Modifier.ABSTRACT;
        assertEquals(what + ": modifiers", expected & checked, actual & checked);
    }

    private static void assertMethod(String owner, String name, String descriptor,
            int modifiers) throws Exception {
        assertMethod(load(owner), name, descriptor, modifiers);
    }

    private static void assertMethod(Class<?> owner, String name, String descriptor,
            int modifiers) throws Exception {
        Method method = declaredMethod(owner, name, descriptor);
        assertModifiers(owner.getName() + "#" + name + descriptor, modifiers,
                method.getModifiers());
    }

    private static Method declaredMethod(Class<?> owner, String name, String descriptor)
            throws Exception {
        Class<?>[] parameters = parameterTypes(descriptor);
        Class<?> returns = returnType(descriptor);
        for (Method method : owner.getDeclaredMethods()) {
            if (method.getName().equals(name)
                    && java.util.Arrays.equals(method.getParameterTypes(), parameters)
                    && method.getReturnType().equals(returns)) {
                return method;
            }
        }
        throw new NoSuchMethodException(owner.getName() + "#" + name + descriptor);
    }

    private static void assertField(String owner, String name, String descriptor,
            int modifiers) throws Exception {
        Field field = load(owner).getDeclaredField(name);
        assertEquals(owner + "." + name + descriptor + ": field type",
                fieldType(descriptor), field.getType());
        assertModifiers(owner + "." + name, modifiers, field.getModifiers());
    }

    private static void assertConstructor(String owner, String descriptor,
            int modifiers) throws Exception {
        Constructor<?> constructor =
                load(owner).getDeclaredConstructor(parameterTypes(descriptor));
        assertModifiers(owner + ".<init>" + descriptor, modifiers,
                constructor.getModifiers());
    }

    private static void assertNoVisibleMethod(String owner, String name,
            String descriptor) throws Exception {
        try {
            Method method = declaredMethod(load(owner), name, descriptor);
            assertEquals(owner + "#" + name + " must not be public/protected",
                    0, method.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchMethodException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertNoVisibleField(String owner, String name,
            String descriptor) throws Exception {
        try {
            Field field = load(owner).getDeclaredField(name);
            assertEquals(owner + "." + name + " must not be public/protected",
                    0, field.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchFieldException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertNoVisibleConstructor(String owner, String descriptor)
            throws Exception {
        try {
            Constructor<?> constructor =
                    load(owner).getDeclaredConstructor(parameterTypes(descriptor));
            assertEquals(owner + ".<init> must not be public/protected",
                    0, constructor.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchMethodException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertClassModifiers(String owner, int modifiers)
            throws Exception {
        // Class.getModifiers() folds in InnerClasses-attribute STATIC, which
        // class-file access flags (the audited layer) never carry.
        int checked = VISIBILITY | Modifier.FINAL | Modifier.ABSTRACT;
        assertEquals(owner + ": class modifiers", modifiers & checked,
                load(owner).getModifiers() & checked);
    }

    /** Asserts the class file's constant pool contains the exact signature. */
    private static void assertClassfileContains(String owner, String constant)
            throws Exception {
        Class<?> clazz = load(owner);
        String resource = "/" + owner.replace('.', '/') + ".class";
        try (InputStream stream = clazz.getResourceAsStream(resource)) {
            assertNotNull(owner + ": class bytes", stream);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = stream.read(buffer)) != -1) {
                bytes.write(buffer, 0, read);
            }
            String text = new String(bytes.toByteArray(), StandardCharsets.ISO_8859_1);
            assertTrue(owner + " must keep signature constant " + constant,
                    text.contains(constant));
        }
    }

    private static void assertMetadataHashes(String owner, String d1Sha256,
            String d2Sha256) throws Exception {
        kotlin.Metadata metadata = load(owner).getAnnotation(kotlin.Metadata.class);
        assertNotNull(owner + ": kotlin.Metadata", metadata);
        assertEquals(owner + ": metadata d1", d1Sha256, sha256(metadata.d1()));
        assertEquals(owner + ": metadata d2", d2Sha256, sha256(metadata.d2()));
    }

    private static String sha256(String[] values) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                String.join(" ", values).getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
"""


def java_escape(text):
    return text.replace("\\", "\\\\").replace('"', '\\"')


if __name__ == "__main__":
    main()

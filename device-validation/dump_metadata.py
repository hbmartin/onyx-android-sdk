#!/usr/bin/env python3
"""Dump a class's kotlin.Metadata annotation from a jar as Java literals.

usage: dump_metadata.py <jar> <binary.class.Name>

Prints each annotation element (mv, k, xi, d1, d2, ...) with string values
escaped exactly as a Java source literal needs them, so the output can be
pasted into a recovered @Metadata(...) annotation.
"""

import struct
import sys
import zipfile


def parse_constant_pool(data, offset, count):
    pool = {}
    index = 1
    while index < count:
        tag = data[offset]
        offset += 1
        if tag == 1:  # Utf8
            length = struct.unpack_from(">H", data, offset)[0]
            raw = data[offset + 2:offset + 2 + length]
            pool[index] = ("utf8", raw)
            offset += 2 + length
        elif tag in (3, 4):  # Integer, Float
            pool[index] = ("int", struct.unpack_from(">i", data, offset)[0]) \
                if tag == 3 else ("float", struct.unpack_from(">f", data, offset)[0])
            offset += 4
        elif tag in (5, 6):  # Long, Double (take two slots)
            pool[index] = ("long", struct.unpack_from(">q", data, offset)[0]) \
                if tag == 5 else ("double", struct.unpack_from(">d", data, offset)[0])
            offset += 8
            index += 1
        elif tag in (7, 8, 16, 19, 20):  # Class, String, MethodType, Module, Package
            pool[index] = ("ref", struct.unpack_from(">H", data, offset)[0])
            offset += 2
        elif tag in (9, 10, 11, 12, 17, 18):  # refs and NameAndType, Dynamic
            offset += 4
        elif tag == 15:  # MethodHandle
            offset += 3
        else:
            raise ValueError(f"unknown constant tag {tag}")
        index += 1
    return pool, offset


def modified_utf8(raw):
    # Modified UTF-8: embedded NUL is encoded as 0xC0 0x80; surrogate pairs
    # may appear as raw 3-byte sequences (CESU-8), which surrogatepass keeps.
    return raw.replace(b"\xc0\x80", b"\x00").decode("utf-8", errors="surrogatepass")


def java_escape(text):
    out = []
    for ch in text:
        code = ord(ch)
        if ch == '"':
            out.append('\\"')
        elif ch == "\\":
            out.append("\\\\")
        elif ch == "\n":
            out.append("\\n")
        elif ch == "\r":
            out.append("\\r")
        elif ch == "\t":
            out.append("\\t")
        elif 0x20 <= code < 0x7f:
            out.append(ch)
        else:
            if code > 0xFFFF:
                units = ch.encode("utf-16-be")
                for hi, lo in zip(units[::2], units[1::2]):
                    out.append(f"\\u{(hi << 8) | lo:04x}")
            else:
                out.append(f"\\u{code:04x}")
    return '"' + "".join(out) + '"'


def read_element_value(data, offset, pool):
    tag = chr(data[offset])
    offset += 1
    if tag in "BCDFIJSZsc":
        index = struct.unpack_from(">H", data, offset)[0]
        offset += 2
        kind, value = pool[index]
        if kind == "ref":
            kind, value = pool[value]
        if kind == "utf8":
            value = modified_utf8(value)
        return (tag, value), offset
    if tag == "e":
        offset += 4
        return (tag, None), offset
    if tag == "[":
        count = struct.unpack_from(">H", data, offset)[0]
        offset += 2
        values = []
        for _ in range(count):
            value, offset = read_element_value(data, offset, pool)
            values.append(value)
        return (tag, values), offset
    if tag == "@":
        annotation, offset = read_annotation(data, offset, pool)
        return (tag, annotation), offset
    raise ValueError(f"unhandled element tag {tag}")


def read_annotation(data, offset, pool):
    type_index = struct.unpack_from(">H", data, offset)[0]
    pair_count = struct.unpack_from(">H", data, offset + 2)[0]
    offset += 4
    name = modified_utf8(pool[type_index][1])
    pairs = []
    for _ in range(pair_count):
        element_index = struct.unpack_from(">H", data, offset)[0]
        offset += 2
        element_name = modified_utf8(pool[element_index][1])
        value, offset = read_element_value(data, offset, pool)
        pairs.append((element_name, value))
    return (name, pairs), offset


def format_value(value):
    tag, payload = value
    if tag == "[":
        return "{ " + ", ".join(format_value(v) for v in payload) + " }"
    if tag == "s":
        return java_escape(payload)
    return str(payload)


def main():
    jar, class_name = sys.argv[1], sys.argv[2]
    entry = class_name.replace(".", "/") + ".class"
    with zipfile.ZipFile(jar) as archive:
        data = archive.read(entry)
    assert data[:4] == b"\xca\xfe\xba\xbe"
    pool_count = struct.unpack_from(">H", data, 8)[0]
    pool, offset = parse_constant_pool(data, 10, pool_count)
    offset += 6  # access, this, super
    interfaces = struct.unpack_from(">H", data, offset)[0]
    offset += 2 + interfaces * 2
    for _section in ("fields", "methods"):
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
        attr_name = modified_utf8(pool[name_index][1])
        body = offset + 6
        if attr_name == "RuntimeVisibleAnnotations":
            annotation_count = struct.unpack_from(">H", data, body)[0]
            cursor = body + 2
            for _ in range(annotation_count):
                (annotation_type, pairs), cursor = read_annotation(data, cursor, pool)
                if annotation_type == "Lkotlin/Metadata;":
                    print(f"// {class_name}")
                    for element_name, value in pairs:
                        print(f"{element_name} = {format_value(value)}")
                    return
        offset += 6 + length
    print(f"// {class_name}: no kotlin.Metadata annotation found")


if __name__ == "__main__":
    main()

package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;

public class DateTimeAndUuidUtilsTest {
    @Test
    public void monthLengthUsesProlepticGregorianRules() {
        assertEquals(29, DateTimeUtil.getMonthDaysCount(2024, 2));
        assertEquals(28, DateTimeUtil.getMonthDaysCount(2023, 2));
        assertEquals(30, DateTimeUtil.getMonthDaysCount(2024, 4));
        assertEquals(31, DateTimeUtil.getMonthDaysCount(2024, 12));
        assertThrows(IllegalArgumentException.class,
                () -> DateTimeUtil.getMonthDaysCount(2024, 0));
        assertThrows(IllegalArgumentException.class,
                () -> DateTimeUtil.getMonthDaysCount(2024, 13));
    }

    @Test
    public void leapYearUsesProlepticGregorianRules() {
        assertTrue(DateTimeUtil.isLeapYear(2000));
        assertTrue(DateTimeUtil.isLeapYear(2024));
        assertFalse(DateTimeUtil.isLeapYear(1900));
        assertFalse(DateTimeUtil.isLeapYear(2023));
    }

    @Test
    public void epochUuidHasVersionVariantAndCurrentTimestamp() {
        long before = System.currentTimeMillis();
        UUID uuid = UUID.fromString(UUIDUtils.timeBasedEpochUUID());
        long after = System.currentTimeMillis();
        long timestamp = uuid.getMostSignificantBits() >>> 16;

        assertEquals(7, uuid.version());
        assertEquals(2, uuid.variant());
        assertTrue(timestamp >= before);
        assertTrue(timestamp <= after);
    }

    @Test
    public void epochUuidsAreUniqueAndStrictlyOrdered() {
        Set<UUID> values = new HashSet<>();
        UUID previous = UUID.fromString(UUIDUtils.timeBasedEpochUUID());
        values.add(previous);
        for (int i = 0; i < 2000; i++) {
            UUID current = UUID.fromString(UUIDUtils.timeBasedEpochUUID());
            assertTrue(previous.compareTo(current) < 0);
            assertTrue(values.add(current));
            previous = current;
        }
    }
}

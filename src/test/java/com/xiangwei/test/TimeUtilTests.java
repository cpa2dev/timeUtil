package com.xiangwei.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TimeUtilTests {

    private static TimeUtil timeUtil = null;
    private static String mockTime = "9:13 AM";
    private static final int minutesOfDay = 24 * 60;

    @BeforeClass
    public static void initialize() throws Exception {
        timeUtil = new TimeUtil();
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        timeUtil = null;
    }

    @Test
    public void testValidateValidTime() {
        assertTrue(timeUtil.isValidTime("0:00 AM"));
        assertTrue(timeUtil.isValidTime("0:23 AM"));
        assertTrue(timeUtil.isValidTime("1:23 AM"));
        assertTrue(timeUtil.isValidTime("2:00 AM"));
        assertTrue(timeUtil.isValidTime("12:23 AM"));
        assertTrue(timeUtil.isValidTime("1:23 PM"));
        assertTrue(timeUtil.isValidTime("2:00 PM"));
        assertTrue(timeUtil.isValidTime("12:23 PM"));
    }

    @Test
    public void testValidateInvalidTime() {
        assertFalse(timeUtil.isValidTime(""));
        assertFalse(timeUtil.isValidTime(null));
        assertFalse(timeUtil.isValidTime("1:63"));
        assertFalse(timeUtil.isValidTime("1.63 AM"));
        assertFalse(timeUtil.isValidTime("1:63 AM"));
        assertFalse(timeUtil.isValidTime("13:23 AM"));
        assertFalse(timeUtil.isValidTime("1:63 PM"));
        assertFalse(timeUtil.isValidTime("13:23 PM"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTimeByPassingInvalidTime() throws IllegalArgumentException {
        timeUtil.getTime(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTimeByPassingInvalidTimeEmptyString() throws IllegalArgumentException {
        timeUtil.getTime("", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTimeByPassingInvalidTimeNull() throws IllegalArgumentException {
        timeUtil.getTime("13:16 PM", 0);
    }

    @Test
    public void testGetTimeByAddingSubtractingDays() throws IllegalArgumentException {
        assertEquals(timeUtil.getTime(mockTime, 0), mockTime);
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay), mockTime);
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 2), mockTime);
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 12), mockTime);
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay), mockTime);
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay * 2), mockTime);
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay * 12), mockTime);
    }

    @Test
    public void testGetTimeByAddingMinutes() throws IllegalArgumentException {
        assertEquals(timeUtil.getTime(mockTime, 10), "9:23 AM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay + 10), "9:23 AM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 2 + 10), "9:23 AM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 15 + 10), "9:23 AM");
        // Morning > Afternoon
        assertEquals(timeUtil.getTime(mockTime, 200), "12:33 PM");
        assertEquals(timeUtil.getTime(mockTime, 320), "2:33 PM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay + 200), "12:33 PM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay + 320), "2:33 PM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 3 + 200), "12:33 PM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay * 5 + 320), "2:33 PM");
        assertEquals(timeUtil.getTime(mockTime, 320), "2:33 PM");
        // To tomorrow
        assertEquals(timeUtil.getTime(mockTime, 920), "0:33 AM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay + 1040), "2:33 AM");
    }

    @Test
    public void testGetTimeBySubtractingMinutes() throws IllegalArgumentException {
        assertEquals(timeUtil.getTime(mockTime, -10), "9:03 AM");
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay - 10), "9:03 AM");
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay * 2 - 10), "9:03 AM");
        assertEquals(timeUtil.getTime(mockTime, -minutesOfDay * 15 - 10), "9:03 AM");
        // Afternoon to morning
        assertEquals(timeUtil.getTime("12:33 PM", -200), mockTime);
        assertEquals(timeUtil.getTime("2:33 PM", -320), mockTime);
        // To day before today
        assertEquals(timeUtil.getTime(mockTime, -560), "11:53 PM");
        assertEquals(timeUtil.getTime(mockTime, minutesOfDay - 560), "11:53 PM");
    }
}

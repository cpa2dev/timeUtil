package com.xiangwei.test;

/**
 * This is time helper class. It provides the method to get a new time by adding / subtracting a mount of minutes on a specified time.
 *
 * Supported time format "[H]H:MM {AM|PM}"
 */
public class TimeUtil {

    private final String am = "AM";
    private final String  pm = "PM";
    private final int twelveHour = 12;
    private final int minutesPerHour = 60;
    private final int minutesPerDay = 24 * 60;

    /**
     * Return a new time by adding / subtracting a mount of minutes.
     *
     * @param time The start time
     * @param offsetInMinute The minutes will be added or subtracted
     * @return A new string time
     * @throws IllegalArgumentException
     */
    public String getTime(String time, int offsetInMinute) throws IllegalArgumentException {
        // Validate the passed time
        if (!isValidTime(time)) {
            throw new IllegalArgumentException("Invalid passing time, the format should be like [H]H:MM {AM|PM}");
        }
        // Format the start time by removing spacing in both end and make it upper case.
        time = time.trim().toUpperCase();

        // Remove days to make it less than a day
        int finalOffset = offsetInMinute % minutesPerDay;
        if (finalOffset == 0) {
            // Return it directly if the offset is '0' or full days
            return time;
        }

        // Convent the pass time to an integer to add the offset minutes, then convert it back.
        int timeValue = timeToInt(time);
        timeValue += finalOffset;
        timeValue %= minutesPerDay;
        String result = intToTime(timeValue);

        // Done
        return result;
    }

    /**
     * Checking if a string is valid time format.
     *
     * @param time The time string.
     * @return True if it's valid, otherwise false.
     */
    public boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) {
            return false;
        }

        String regex = "([1-9]|1[0-2]):[0-5][0-9] (AM|PM)";
        return time.trim().matches(regex);
    }

    /**
     * Convert a string time to an integer value which represents the passed minutes in a day.
     *
     * @param time The string time.
     * @return A integer represents the passed minutes.
     */
    private int timeToInt(String time) {
        int colonIndex = time.indexOf(":");
        int spaceIndex = time.indexOf(" ");
        int hour = Integer.parseInt(time.substring(0, colonIndex));
        int minute = Integer.parseInt(time.substring(colonIndex + 1, spaceIndex));
        // Edge case: add 12 hours when it's PM but not for 12:01 PM
        int addOnMinute = time.substring(spaceIndex + 1).equalsIgnoreCase(am) || hour == twelveHour ? 0 : minutesPerDay / 2;

        // Done
        return hour * minutesPerHour + minute + addOnMinute;
    }

    /**
     * Covert the passed minutes to time.
     *
     * @param minutes The passed minutes in a day.
     * @return The string time
     */
    private String intToTime(int minutes) {
        if (minutes < 0) {
            // Convert it to positive when it is negative
            minutes += minutesPerDay;
        }
        int hour = minutes / minutesPerHour;
        int minute = minutes % minutesPerHour;

        // Convert to 12 format hour
        int twelveFormatHour = (hour <= 12 ? hour : hour % twelveHour);
        // Convert to 2 digit minute
        String twoDigitsMinute = minute < 10 ? "0"+minute : "" + minute;
        String amPmSign = (hour >= twelveHour ? pm : am);
        // Compose time
        String result = String.format("%d:%s %s", twelveFormatHour, twoDigitsMinute, amPmSign);

        // Done
        return result;
    }
}

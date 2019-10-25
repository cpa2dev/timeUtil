## TimeUtil Description

This is a time helper method by without using any built-in date or time functions. It accepts two mandatory arguments: the first argument is a 12-hour time string with the format "[H]H:MM
{AM|PM}", and the second argument is a (signed) integer. The second argument is the number of
minutes to add to the time of day represented by the first argument. The return value is a
string of the same format as the first argument. 

For example, AddMinutes("9:13 AM", 200) would
return "12:33 PM". 

## Implementation Approach

First, validate passing time with regex matching, throw an exception if it's invalid. This guarantee the returned time alwasy be valid.

Second, check passing minute offset, return the passing time directly if it's zero or N times of day minutes(60*24).

Then, convert the passing time to integer which represents the passed minutes in a day, the range will be `0 ~ 24*60`, and then add the minutes offset, then convert it back to time string, there are some corner cases need to handle properly.

Finally, return the result.

## : getTime(String time, int offsetInMinute)

* Passing parameters
   - `time` - A time string, throw exception of `IllegalArgumentException` if it's invalid.
   - `offsetInMinute` - A integer of offset in minute.

* Return a time string or throw `IllegalArgumentException`.


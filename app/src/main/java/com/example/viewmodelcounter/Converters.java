package com.example.viewmodelcounter;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    // DATES CONVERTER ///////////////////
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromStringDate(String strdate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return strdate == null ? null : format.parse(strdate);
    }

    @TypeConverter
    public static String dateToStringDate(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return date == null ? null : format.format(date);
    }
}

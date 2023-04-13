package ru.student.detected.educator.data.data_sources.room.converters;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        return Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        return String.join(",", list);
    }
}

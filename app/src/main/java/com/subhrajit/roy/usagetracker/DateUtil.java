package com.subhrajit.roy.usagetracker;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {

    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DD_MM_YYYY, Locale.ENGLISH);
}

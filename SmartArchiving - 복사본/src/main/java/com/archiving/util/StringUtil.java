package com.archiving.util;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

    private StringUtil() {}

    public static String nvlTrim(String value) {
        return value == null ? "" : value.trim();
    }

    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }

    public static boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }
}

package com.oss.unist.hr.util;

public class ValidationUtils {
    public static boolean isStringNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}

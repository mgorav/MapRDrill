package com.gm.util;

import java.util.Collection;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

public class Check {

    public static void isValid(boolean expression, String message, Object... args) {

        if (!expression) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static void notNull(Object obj, String message, Object... args) {

        if (obj == null) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static void notEmpty(Collection<?> col, String message, Object... args) {

        if (col == null || col.isEmpty()) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static void notEmpty(Object[] ary, String message, Object... args) {

        if (ary == null || ary.length == 0) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static void notBlank(String str, String message, Object... args) {

        if (isEmpty(str)) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static boolean isNumeric(String str) {

        if (!isEmpty(str)) {
            try {
                Integer.valueOf(str);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }
}


/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.4.5
 */
public final class MessageFormatter {

    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    private MessageFormatter() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static String format(String messagePattern, Object arg) {
        return format(messagePattern, new Object[]{arg});
    }

    public static String format(final String messagePattern, final Object[] argArray) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(argArray);
        Object[] args = argArray;
        if (throwableCandidate != null) {
            args = MessageFormatter.trimmedCopy(argArray);
        }
        return format(messagePattern, args, throwableCandidate);
    }

    private static String format(final String messagePattern, final Object[] argArray, Throwable throwable) {

        if (messagePattern == null) {
            return null;
        }

        if (argArray == null) {
            return messagePattern;
        }

        int i = 0;
        int j;
        // use string builder for better multicore performance
        StringBuilder builder = new StringBuilder(messagePattern.length() + 50);

        int L;
        for (L = 0; L < argArray.length; L++) {

            j = messagePattern.indexOf(DELIM_STR, i);

            if (j == -1) {
                // no more variables
                if (i == 0) { // this is a simple string
                    return messagePattern;
                } else { // add the tail string which contains no variables and return
                    // the result.
                    builder.append(messagePattern, i, messagePattern.length());
                    return messagePattern;
                }
            } else {
                if (isEscapedDelimeter(messagePattern, j)) {
                    if (!isDoubleEscaped(messagePattern, j)) {
                        L--; // DELIM_START was escaped, thus should not be incremented
                        builder.append(messagePattern, i, j - 1);
                        builder.append(DELIM_START);
                        i = j + 1;
                    } else {
                        // The escape character preceding the delimiter start is
                        // itself escaped: "abc x:\\{}"
                        // we have to consume one backward slash
                        builder.append(messagePattern, i, j - 1);
                        deeplyAppendParameter(builder, argArray[L], new HashMap<>());
                        i = j + 2;
                    }
                } else {
                    // normal case
                    builder.append(messagePattern, i, j);
                    deeplyAppendParameter(builder, argArray[L], new HashMap<>());
                    i = j + 2;
                }
            }
        }
        // append the characters following the last {} pair.
        builder.append(messagePattern, i, messagePattern.length());
        return builder.toString();
    }

    private static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
        if (delimeterStartIndex == 0) {
            return false;
        }
        char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
        return potentialEscape == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
        return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
    }

    private static void deeplyAppendParameter(StringBuilder builder, Object o, Map<Object[], Object> seenMap) {
        if (o == null) {
            builder.append("null");
            return;
        }
        if (!o.getClass().isArray()) {
            safeObjectAppend(builder, o);
        } else {
            // check for primitive array types because they
            // unfortunately cannot be cast to Object[]
            if (o instanceof boolean[]) {
                booleanArrayAppend(builder, (boolean[]) o);
            } else if (o instanceof byte[]) {
                byteArrayAppend(builder, (byte[]) o);
            } else if (o instanceof char[]) {
                charArrayAppend(builder, (char[]) o);
            } else if (o instanceof short[]) {
                shortArrayAppend(builder, (short[]) o);
            } else if (o instanceof int[]) {
                intArrayAppend(builder, (int[]) o);
            } else if (o instanceof long[]) {
                longArrayAppend(builder, (long[]) o);
            } else if (o instanceof float[]) {
                floatArrayAppend(builder, (float[]) o);
            } else if (o instanceof double[]) {
                doubleArrayAppend(builder, (double[]) o);
            } else {
                objectArrayAppend(builder, (Object[]) o, seenMap);
            }
        }
    }

    private static void safeObjectAppend(StringBuilder builder, Object o) {
        try {
            String oAsString = o.toString();
            builder.append(oAsString);
        } catch (Throwable t) {
            builder.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder builder, Object[] a, Map<Object[], Object> seenMap) {
        builder.append('[');
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null);
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                deeplyAppendParameter(builder, a[i], seenMap);
                if (i != len - 1)
                    builder.append(", ");
            }
            // allow repeats in siblings
            seenMap.remove(a);
        } else {
            builder.append("...");
        }
        builder.append(']');
    }

    private static void booleanArrayAppend(StringBuilder builder, boolean[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void byteArrayAppend(StringBuilder builder, byte[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void charArrayAppend(StringBuilder builder, char[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void shortArrayAppend(StringBuilder builder, short[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void intArrayAppend(StringBuilder builder, int[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void longArrayAppend(StringBuilder builder, long[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void floatArrayAppend(StringBuilder builder, float[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    private static void doubleArrayAppend(StringBuilder builder, double[] a) {
        builder.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            builder.append(a[i]);
            if (i != len - 1)
                builder.append(", ");
        }
        builder.append(']');
    }

    /**
     * Helper method to determine if an {@link Object} array contains a {@link Throwable} as last element
     *
     * @param argArray The arguments off which we want to know if it contains a {@link Throwable} as last element
     * @return if the last {@link Object} in argArray is a {@link Throwable} this method will return it,
     * otherwise it returns null
     */
    public static Throwable getThrowableCandidate(final Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            return null;
        }

        final Object lastEntry = argArray[argArray.length - 1];
        if (lastEntry instanceof Throwable) {
            return (Throwable) lastEntry;
        }

        return null;
    }

    /**
     * Helper method to get all but the last element of an array
     *
     * @param argArray The arguments from which we want to remove the last element
     * @return a copy of the array without the last element
     */
    public static Object[] trimmedCopy(final Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }

        final int trimmedLen = argArray.length - 1;

        Object[] trimmed = new Object[trimmedLen];

        if (trimmedLen > 0) {
            System.arraycopy(argArray, 0, trimmed, 0, trimmedLen);
        }

        return trimmed;
    }

}

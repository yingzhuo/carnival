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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 */
@Deprecated
public final class Strings {

    private Strings() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static boolean isBlank(String string) {
        if (string == null) return true;

        return string
                .chars()
                .allMatch(Character::isWhitespace);
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static boolean isEmpty(String string) {
        if (string == null) return true;
        return string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static String nullToEmpty(String string) {
        return string == null ? "" : string;
    }

    public static String emptyToNull(String string) {
        return isEmpty(string) ? null : string;
    }

    public static String blankToNull(String string) {
        return isBlank(string) ? null : string;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static boolean containsSpace(String string) {
        Validate.notNull(string);
        return toCharStream(string).anyMatch(ch -> ch == ' ');
    }

    public static boolean containsWhitespace(String string) {
        Validate.notNull(string);
        return toCharStream(string).anyMatch(Character::isWhitespace);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Stream<Character> toCharStream(String string) {
        if (string == null) {
            return Stream.empty();
        }
        return string.chars().mapToObj(ch -> (char) ch);
    }

    public static Set<Character> toCharSet(String string) {
        return Collections.unmodifiableSet(
                toCharStream(string).collect(Collectors.toSet())
        );
    }

    public static SortedSet<Character> toCharSortedSet(String string) {
        return Collections.unmodifiableSortedSet(
                toCharStream(string).collect(Collectors.toCollection(TreeSet::new))
        );
    }

    public static List<Character> toCharList(String string) {
        return Collections.unmodifiableList(
                toCharStream(string).collect(Collectors.toList())
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static String reverse(String string) {
        return new StringBuilder(Validate.notNull(string))
                .reverse()
                .toString();
    }

}

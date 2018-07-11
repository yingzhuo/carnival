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
public class StringUtils {

    private StringUtils() {
        super();
    }

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

}

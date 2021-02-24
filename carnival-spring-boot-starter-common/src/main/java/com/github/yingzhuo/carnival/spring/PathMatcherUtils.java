/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 1.6.13
 */
public final class PathMatcherUtils {

    private static final PathMatcher DEFAULT = new AntPathMatcher();

    private PathMatcherUtils() {
    }

    public static boolean match(String path, String pattern) {
        Assert.hasText(pattern, "pattern is empty");
        Assert.hasText(path, "path is empty");
        final PathMatcher matcher = SpringUtils.getBean(PathMatcher.class, DEFAULT);
        return matcher.match(pattern, path);
    }

    public static boolean anyMatch(String path, String... patterns) {
        Assert.hasText(path, "path is empty");
        Assert.notEmpty(patterns, "patterns is empty");

        final PathMatcher matcher = SpringUtils.getBean(PathMatcher.class, DEFAULT);
        if (patterns.length == 1) {
            return match(path, patterns[0]);
        }

        return Stream.of(patterns).anyMatch(pattern -> matcher.match(pattern, path));
    }

    public static boolean anyMatch(String path, Collection<String> patterns) {
        return anyMatch(path, patterns.toArray(new String[0]));
    }

    public static boolean allMatch(String path, String... patterns) {
        Assert.hasText(path, "path is empty");
        Assert.notEmpty(patterns, "patterns is empty");

        final PathMatcher matcher = SpringUtils.getBean(PathMatcher.class, DEFAULT);
        if (patterns.length == 1) {
            return match(path, patterns[0]);
        }

        return Stream.of(patterns).allMatch(pattern -> matcher.match(pattern, path));
    }

    public static boolean allMatch(String path, Collection<String> patterns) {
        return allMatch(path, patterns.toArray(new String[0]));
    }

}

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

import org.springframework.util.PathMatcher;

import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 1.6.13
 */
public final class PathMatcherUtils {

    private PathMatcherUtils() {
    }

    public static boolean anyMatch(String path, String... patterns) {
        final PathMatcher matcher = SpringUtils.getBean(PathMatcher.class);
        return Stream.of(patterns).anyMatch(pattern -> matcher.match(pattern, path));
    }

    public static boolean allMatch(String path, String... patterns) {
        final PathMatcher matcher = SpringUtils.getBean(PathMatcher.class);
        return Stream.of(patterns).allMatch(pattern -> matcher.match(pattern, path));
    }

}

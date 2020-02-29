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

import org.springframework.core.env.Profiles;

import java.util.*;

/**
 * Spring相关通用工具
 *
 * @author 应卓
 * @see SpringUtils
 */
public final class ProfileUtils {

    private ProfileUtils() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static List<String> getActiveProfiles() {
        return Collections.unmodifiableList(Arrays.asList(SpringUtils.getEnvironment().getActiveProfiles()));
    }

    public static SortedSet<String> getActiveProfilesAsSet() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(getActiveProfiles()));
    }

    public static List<String> getDefaultProfiles() {
        return Collections.unmodifiableList(Arrays.asList(SpringUtils.getEnvironment().getDefaultProfiles()));
    }

    public static SortedSet<String> getDefaultProfilesAsSet() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(getDefaultProfiles()));
    }

    public static boolean allActive(String... profiles) {
        return allActive(Profiles.of(profiles));
    }

    public static boolean allActive(Profiles... profiles) {
        return Arrays.stream(profiles).allMatch(SpringUtils.getEnvironment()::acceptsProfiles);
    }

    public static boolean anyActive(String... profiles) {
        return anyActive(Profiles.of(profiles));
    }

    public static boolean anyActive(Profiles... profiles) {
        return Arrays.stream(profiles).anyMatch(SpringUtils.getEnvironment()::acceptsProfiles);
    }

    public static boolean noneActive(String... profiles) {
        return noneActive(Profiles.of(profiles));
    }

    public static boolean noneActive(Profiles... profiles) {
        return Arrays.stream(profiles).noneMatch(SpringUtils.getEnvironment()::acceptsProfiles);
    }

}

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

import org.springframework.core.env.Environment;

import java.util.*;
import java.util.function.Consumer;

/**
 * Spring相关通用工具
 *
 * @author 应卓
 * @see SpringUtils
 */
public final class ProfileUtils {

    private ProfileUtils() {
        super();
    }

    public static Environment getEnvironment() {
        return SpringUtils.ENV;
    }

    public static List<String> getActiveProfiles() {
        return Collections.unmodifiableList(Arrays.asList(getEnvironment().getActiveProfiles()));
    }

    public static SortedSet<String> getActiveProfilesAsSet() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(getActiveProfiles()));
    }

    public static List<String> getDefaultProfiles() {
        return Collections.unmodifiableList(Arrays.asList(getEnvironment().getDefaultProfiles()));
    }

    public static SortedSet<String> getDefaultProfilesAsSet() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(getDefaultProfiles()));
    }

    public static boolean allActive(String... profiles) {
        return Arrays.stream(profiles).allMatch(getEnvironment()::acceptsProfiles);
    }

    public static boolean anyActive(String... profiles) {
        return Arrays.stream(profiles).anyMatch(getEnvironment()::acceptsProfiles);
    }

    public static boolean noneActive(String... profiles) {
        return Arrays.stream(profiles).noneMatch(getEnvironment()::acceptsProfiles);
    }

    public static void ifActive(String profileName, Consumer<String> ifTrue) {
        if (getEnvironment().acceptsProfiles(profileName)) {
            ifTrue.accept(profileName);
        }
    }

    public static void ifActive(String profileName, Consumer<String> ifTrue, Consumer<String> ifFalse) {

        Objects.requireNonNull(profileName);

        if (getEnvironment().acceptsProfiles(profileName)) {
            Objects.requireNonNull(ifTrue).accept(profileName);
        } else {
            Objects.requireNonNull(ifFalse).accept(profileName);
        }
    }

}

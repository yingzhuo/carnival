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
 * @since 0.0.1
 */
final public class ProfileUtils {

    private ProfileUtils() {
    }

    public static Environment getEnvironment() {
        return SpringUtils.ENV;
    }

    public static List<String> getActivedProfiles() {
        return Collections.unmodifiableList(Arrays.asList(getEnvironment().getActiveProfiles()));
    }

    public static Set<String> getActivedProfilesAsSet() {
        return Collections.unmodifiableSet(new HashSet<>(getActivedProfiles()));
    }

    public static List<String> getDefaultProfiles() {
        return Collections.unmodifiableList(Arrays.asList(getEnvironment().getDefaultProfiles()));
    }

    public static Set<String> getDefaultProfilesAsSet() {
        return Collections.unmodifiableSet(new HashSet<>(getDefaultProfiles()));
    }

    public static void whenActived(String profileName, Consumer<String> ifTrue) {
        if (getEnvironment().acceptsProfiles(profileName)) {
            ifTrue.accept(profileName);
        }
    }

    public static void whenActived(String profileName, Consumer<String> ifTrue, Consumer<String> ifFlase) {
        if (getEnvironment().acceptsProfiles(profileName)) {
            ifTrue.accept(profileName);
        } else {
            ifFlase.accept(profileName);
        }
    }

}

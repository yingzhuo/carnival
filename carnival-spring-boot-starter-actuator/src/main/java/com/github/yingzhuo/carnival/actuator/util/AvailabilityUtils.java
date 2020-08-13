/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;

/**
 * @author 应卓
 * @since 1.7.1
 */
public final class AvailabilityUtils {

    private AvailabilityUtils() {
    }

    public static ReadinessState getReadinessState() {
        return SpringUtils.getBean(ApplicationAvailability.class).getReadinessState();
    }

    public static LivenessState getLivenessState() {
        return SpringUtils.getBean(ApplicationAvailability.class).getLivenessState();
    }

    public static boolean isReady() {
        return getReadinessState() == ReadinessState.ACCEPTING_TRAFFIC;
    }

    public static boolean isLive() {
        return getLivenessState() == LivenessState.CORRECT;
    }

}

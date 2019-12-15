/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.meter.util;

import com.github.yingzhuo.carnival.meter.props.MeterLabelConfig;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.3.5
 */
public final class MeterLabelUtils {

    public static String getLabelProject() {
        try {
            return SpringUtils.getBean(MeterLabelConfig.class).getProject();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLabelApplication() {
        try {
            return SpringUtils.getBean(MeterLabelConfig.class).getApplication();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLabelLayer() {
        try {
            return SpringUtils.getBean(MeterLabelConfig.class).getLayer();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLabelVersion() {
        try {
            return SpringUtils.getBean(MeterLabelConfig.class).getVersion();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLabelPrimaryProfile() {
        try {
            return SpringUtils.getBean(MeterLabelConfig.class).getPrimaryProfile();
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    private MeterLabelUtils() {
    }

}

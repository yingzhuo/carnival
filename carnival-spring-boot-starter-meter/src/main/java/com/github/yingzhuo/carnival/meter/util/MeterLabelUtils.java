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
        return SpringUtils.getBean(MeterLabelConfig.class).getProject();
    }

    public static String getLabelApplication() {
        return SpringUtils.getBean(MeterLabelConfig.class).getApplication();
    }

    public static String getLabelLayer() {
        return SpringUtils.getBean(MeterLabelConfig.class).getLayer();
    }

    public static String getLabelVersion() {
        return SpringUtils.getBean(MeterLabelConfig.class).getVersion();
    }

    private MeterLabelUtils() {
    }

}

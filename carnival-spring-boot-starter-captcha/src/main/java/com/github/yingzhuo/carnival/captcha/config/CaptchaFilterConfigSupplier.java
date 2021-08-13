/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.config;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.10.6
 */
public interface CaptchaFilterConfigSupplier extends Supplier<CaptchaFilterConfig> {

    @Override
    public default CaptchaFilterConfig get() {
        return new CaptchaFilterConfig();
    }

}

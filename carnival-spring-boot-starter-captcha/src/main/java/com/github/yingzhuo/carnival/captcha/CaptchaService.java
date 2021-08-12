/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha;

/**
 * @author Piotr Piastucki
 * @author 应卓
 * @since 1.10.6
 */
@FunctionalInterface
public interface CaptchaService {

    public Captcha getCaptcha();

    public default EncodedCaptcha toEncodedCaptcha() {
        return new EncodedCaptcha(getCaptcha());
    }

}

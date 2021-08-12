/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca;

import org.patchca.service.Captcha;
import org.patchca.service.EncodedCaptcha;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface CaptchaService {

    public Captcha getCaptcha();

    public default EncodedCaptcha toEncodedCaptcha() {
        return new EncodedCaptcha(getCaptcha());
    }

}

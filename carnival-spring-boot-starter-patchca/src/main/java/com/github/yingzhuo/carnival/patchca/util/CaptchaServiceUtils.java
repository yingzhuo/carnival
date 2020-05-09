/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.patchca.service.Captcha;
import org.patchca.service.CaptchaService;
import org.patchca.service.EncodedCaptcha;

/**
 * @author 应卓
 * @since 1.6.3
 */
public final class CaptchaServiceUtils {

    private CaptchaServiceUtils() {
    }

    public static Captcha getCaptcha() {
        return SpringUtils.getBean(CaptchaService.class).getCaptcha();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static EncodedCaptcha getEncodedCaptcha() {
        return SpringUtils.getBean(CaptchaService.class).getEncodedCaptcha();
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.github.yingzhuo.carnival.stateless.captcha.Captcha;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaFactory;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.1.4
 */
public final class CaptchaUtils {

    public static Captcha create(int length) {
        return SpringUtils.getBean(CaptchaFactory.class).create(length);
    }

    public static void save(String captchaId, String captchaValue) {
        SpringUtils.getBean(CaptchaDao.class).save(captchaId, captchaValue);
    }

    public static Optional<String> load(String captchaId) {
        return SpringUtils.getBean(CaptchaDao.class).load(captchaId);
    }

    public static void delete(String captchaId) {
        SpringUtils.getBean(CaptchaDao.class).delete(captchaId);
    }

    public static boolean matches(String submittedCaptcha, String captchaId) {
        return SpringUtils.getBean(CaptchaDao.class).matches(submittedCaptcha, captchaId);
    }

    public static boolean notMatches(String submittedCaptcha, String captchaId) {
        return SpringUtils.getBean(CaptchaDao.class).notMatches(submittedCaptcha, captchaId);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private CaptchaUtils() {
    }

}

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

import com.github.yingzhuo.carnival.patchca.CaptchaDao;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.6.2
 */
public final class CaptchaDaoUtils {

    private CaptchaDaoUtils() {
    }

    public static void save(String accessKey, String patchca) {
        SpringUtils.getBean(CaptchaDao.class).save(accessKey, patchca);
    }

    public static String load(String accessKey) {
        return SpringUtils.getBean(CaptchaDao.class).load(accessKey);
    }

    public static void delete(String accessKey) {
        SpringUtils.getBean(CaptchaDao.class).delete(accessKey);
    }

    public static boolean matches(String accessKey, String submittedCaptcha) {
        return SpringUtils.getBean(CaptchaDao.class).matches(accessKey, submittedCaptcha);
    }

    public static boolean notMatches(String accessKey, String submittedCaptcha) {
        return SpringUtils.getBean(CaptchaDao.class).notMatches(accessKey, submittedCaptcha);
    }

}

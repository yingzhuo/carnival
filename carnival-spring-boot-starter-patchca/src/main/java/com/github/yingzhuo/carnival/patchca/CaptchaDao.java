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

/**
 * @author 应卓
 * @since 1.6.2
 */
public interface CaptchaDao {

    public void save(String accessKey, String patchca);

    public String load(String accessKey);

    public void delete(String accessKey);

    public default boolean matches(String accessKey, String submittedCaptcha) {
        final String captcha = load(accessKey);
        if (captcha == null) {
            return false;
        }
        return captcha.equalsIgnoreCase(submittedCaptcha);
    }

    public default boolean notMatches(String accessKey, String submittedCaptcha) {
        return !matches(accessKey, submittedCaptcha);
    }

}

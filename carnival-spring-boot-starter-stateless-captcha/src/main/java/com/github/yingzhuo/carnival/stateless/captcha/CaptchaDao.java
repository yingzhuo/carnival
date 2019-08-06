/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author 应卓
 */
public interface CaptchaDao {

    public void save(String captchaId, String captchaValue);

    public Optional<String> load(String captchaId);

    public void delete(String captchaId);

    public default boolean validate(String submittedCaptcha, String captchaId) {
        return load(captchaId).filter(s -> StringUtils.equalsIgnoreCase(s, submittedCaptcha)).isPresent();
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.impl;

import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.4.6
 */
public class NopCaptchaDao implements CaptchaDao {

    @Override
    public void save(String captchaId, String captchaValue) {
        // NOP
    }

    @Override
    public Optional<String> load(String captchaId) {
        return Optional.empty();
    }

    @Override
    public void delete(String captchaId) {
        // NOP
    }
}

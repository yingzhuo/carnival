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

public class NopCaptchaDao implements CaptchaDao {

    @Override
    public void save(String id, String value) {

    }

    @Override
    public Optional<String> load(String id) {
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
    }

}

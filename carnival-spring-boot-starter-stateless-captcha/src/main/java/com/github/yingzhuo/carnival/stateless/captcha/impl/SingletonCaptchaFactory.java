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

import com.github.yingzhuo.carnival.stateless.captcha.Captcha;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 应卓
 */
public class SingletonCaptchaFactory implements CaptchaFactory {

    @Override
    public Captcha create(int length) {
        // 非严肃开发可使用
        return Captcha.builder()
                .image(null)
                .id("x")
                .value(StringUtils.repeat('x', length))
                .build();
    }

}

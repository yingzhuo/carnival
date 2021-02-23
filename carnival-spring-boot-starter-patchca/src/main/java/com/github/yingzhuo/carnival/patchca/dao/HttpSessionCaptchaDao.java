/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.dao;

import com.github.yingzhuo.carnival.patchca.CaptchaDao;
import com.github.yingzhuo.carnival.spring.ServletUtils;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class HttpSessionCaptchaDao implements CaptchaDao {

    private static final String ATTRIBUTE_NAME = "com.github.yingzhuo.carnival.patchca.CaptchaSessionAttribute";

    @Override
    public void save(String unused, String captcha) {
        ServletUtils.getSession(true)
                .setAttribute(ATTRIBUTE_NAME, captcha);
    }

    @Override
    public String load(String unused) {
        return (String) ServletUtils.getSession(true)
                .getAttribute(ATTRIBUTE_NAME);
    }

    @Override
    public void delete(String unused) {
        ServletUtils.getSession(true)
                .removeAttribute(ATTRIBUTE_NAME);
    }

}

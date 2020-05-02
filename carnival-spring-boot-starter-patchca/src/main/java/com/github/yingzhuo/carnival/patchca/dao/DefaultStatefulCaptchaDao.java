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
import com.github.yingzhuo.carnival.patchca.CaptchaSessionAttribute;
import com.github.yingzhuo.carnival.spring.ServletUtils;

import javax.servlet.http.HttpSession;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class DefaultStatefulCaptchaDao implements CaptchaDao, CaptchaSessionAttribute {

    @Override
    public void save(String unused, String patchca) {
        final HttpSession session = ServletUtils.getSession(true);
        session.setAttribute(NAME, patchca);
    }

    @Override
    public String load(String unused) {
        final HttpSession session = ServletUtils.getSession(true);
        return (String) session.getAttribute(NAME);
    }

    @Override
    public void delete(String accessKey) {
        final HttpSession session = ServletUtils.getSession(true);
        session.removeAttribute(NAME);
    }

}

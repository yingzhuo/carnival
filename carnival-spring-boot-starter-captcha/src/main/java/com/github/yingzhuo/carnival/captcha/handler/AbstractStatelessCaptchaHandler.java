/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.handler;

import com.github.yingzhuo.carnival.captcha.Captcha;
import com.github.yingzhuo.carnival.captcha.CaptchaHandler;
import com.github.yingzhuo.carnival.captcha.EncodedCaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.10.6
 */
public abstract class AbstractStatelessCaptchaHandler implements CaptchaHandler {

    @Override
    public final void handle(Captcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        doHandle(new EncodedCaptcha(captcha), request, response);
    }

    protected abstract void doHandle(EncodedCaptcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException;

}

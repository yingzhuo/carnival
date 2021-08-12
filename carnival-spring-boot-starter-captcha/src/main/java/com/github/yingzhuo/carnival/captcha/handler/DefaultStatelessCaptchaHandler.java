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

import com.github.yingzhuo.carnival.captcha.EncodedCaptcha;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class DefaultStatelessCaptchaHandler extends AbstractStatelessCaptchaHandler {

    private final boolean outputCaptchaValue;

    public DefaultStatelessCaptchaHandler() {
        this(false);
    }

    public DefaultStatelessCaptchaHandler(boolean outputCaptchaValue) {
        this.outputCaptchaValue = outputCaptchaValue;
    }

    @Override
    protected void doHandle(EncodedCaptcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        final ServletOutputStream os = response.getOutputStream();
        os.println(String.format("accessKey: %s", captcha.getAccessKey()));

        if (outputCaptchaValue) {
            os.println(String.format("captcha: %s", captcha.getCaptcha()));
        }

        os.println(String.format("encodedImage: %s", captcha.getEncodeImage()));

        os.flush();
        os.close();
    }

}

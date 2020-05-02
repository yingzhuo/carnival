/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.handler;

import org.patchca.service.EncodedCaptcha;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class DefaultStatelessCaptchaHandler extends AbstractStatelessCaptchaHandler {

    @Override
    protected void doHandle(EncodedCaptcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ServletOutputStream os = response.getOutputStream();
        os.println(String.format("accessKey: %s", captcha.getAccessKey()));
        os.println(String.format("captcha: %s", captcha.getCaptcha()));
        os.println(String.format("encodedImage: %s", captcha.getEncodeImage()));
        os.flush();
        os.close();
    }

}

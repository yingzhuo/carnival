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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class DefaultStatelessCaptchaHandler extends AbstractStatelessCaptchaHandler {

    @Override
    protected void doHandle(String accessKey, String captcha, String encodedImage, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().println(String.format("accessKey: %s", accessKey));
        response.getOutputStream().println(String.format("captcha: %s", captcha));
        response.getOutputStream().println(String.format("encodedImage: %s", encodedImage));
    }

}

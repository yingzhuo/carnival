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

import com.github.yingzhuo.carnival.patchca.CaptchaHandler;
import org.patchca.service.Captcha;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class DefaultStatefulCaptchaHandler implements CaptchaHandler {

    @Override
    public void handle(Captcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(captcha.getImage(), "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

}

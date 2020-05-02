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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.2
 */
public abstract class AbstractStatelessCaptchaHandler implements CaptchaHandler {

    @Override
    public final void handle(Captcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), "png", out);
        byte[] data = out.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(data);

        this.doHandle(
                captcha.getAccessKey(),
                captcha.getChallenge(),
                encodedImage,
                request,
                response);
    }

    protected abstract void doHandle(String accessKey, String captcha, String encodedImage, HttpServletRequest request, HttpServletResponse response) throws IOException;

}

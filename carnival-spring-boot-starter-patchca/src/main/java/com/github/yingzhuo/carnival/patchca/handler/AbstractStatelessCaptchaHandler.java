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
import org.patchca.service.EncodedCaptcha;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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
        this.doHandle(
                new EncodedCaptcha(captcha, getEncodedImage(captcha.getImage())),
                request,
                response);
    }

    private String getEncodedImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        byte[] bytes = os.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    protected abstract void doHandle(EncodedCaptcha encodedCaptcha, HttpServletRequest request, HttpServletResponse response) throws IOException;

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

/**
 * @author Piotr Piastucki
 * @author 应卓
 * @since 1.6.2
 */
public class EncodedCaptcha extends Captcha {

    private String encodeImage;

    public EncodedCaptcha(Captcha captcha) {
        super.setAccessKey(captcha.getAccessKey());
        super.setCaptcha(captcha.getCaptcha());
        super.setImage(captcha.getImage());
        this.encodeImage = getEncodedImage(captcha.getImage());
    }

    private String getEncodedImage(BufferedImage image) {
        try {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] bytes = os.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getEncodeImage() {
        return encodeImage;
    }

    public void setEncodeImage(String encodeImage) {
        this.encodeImage = encodeImage;
    }

}

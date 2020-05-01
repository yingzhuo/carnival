/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.utils.encoder;

import org.patchca.service.Captcha;
import org.patchca.service.CaptchaService;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Piotr Piastucki
 */
public final class EncoderHelper {

    private EncoderHelper() {
    }

    public static String write(CaptchaService service, String format, OutputStream os) throws IOException {
        Captcha captcha = service.getCaptcha();
        ImageIO.write(captcha.getImage(), format, os);
        return captcha.getChallenge();
    }

}

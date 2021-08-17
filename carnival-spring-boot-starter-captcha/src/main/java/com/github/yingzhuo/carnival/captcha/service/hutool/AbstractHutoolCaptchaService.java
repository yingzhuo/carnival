/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.hutool;

import cn.hutool.captcha.ICaptcha;
import com.github.yingzhuo.carnival.captcha.AccessKeyGenerator;
import com.github.yingzhuo.carnival.captcha.Captcha;
import com.github.yingzhuo.carnival.captcha.CaptchaService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.10.6
 */
public abstract class AbstractHutoolCaptchaService implements CaptchaService {

    private final AccessKeyGenerator accessKeyGenerator;

    public AbstractHutoolCaptchaService() {
        this(null);
    }

    public AbstractHutoolCaptchaService(AccessKeyGenerator accessKeyGenerator) {
        this.accessKeyGenerator = accessKeyGenerator != null ? accessKeyGenerator : AccessKeyGenerator.getDefault();
    }

    @Override
    public final Captcha getCaptcha() {
        final ICaptcha c = createCaptcha();

        final Captcha captcha = new Captcha();
        captcha.setCaptcha(c.getCode());
        captcha.setAccessKey(accessKeyGenerator.generate());
        captcha.setImage(toBufferedImage(c));
        return captcha;
    }

    protected abstract ICaptcha createCaptcha();

    private BufferedImage toBufferedImage(ICaptcha c) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;

        try {
            c.write(out);
            in = new ByteArrayInputStream(out.toByteArray());
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }

        // 注意: in, out 都不需要关闭
    }

}

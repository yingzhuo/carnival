/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.impl;

import com.github.yingzhuo.carnival.stateless.captcha.Captcha;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaFactory;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaIdGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

/**
 * @author 应卓
 */
@Slf4j
public class DefaultCaptchaFactory implements CaptchaFactory {

    private static final Random RANDOM = new Random();
    private final CaptchaDao captchaDao;
    private final CaptchaIdGenerator captchaIdGenerator;
    private int width = 100;
    private int height = 18;

    public DefaultCaptchaFactory(CaptchaDao captchaDao, CaptchaIdGenerator captchaIdGenerator) {
        this.captchaDao = Objects.requireNonNull(captchaDao);
        this.captchaIdGenerator = Objects.requireNonNull(captchaIdGenerator);
    }

    @Override
    public Captcha create(int length) {

        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(getRandColor(200, 250));
            g.fillRect(1, 1, width - 1, height - 1);
            g.setColor(new Color(102, 102, 102));
            g.drawRect(0, 0, width - 1, height - 1);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 17));
            g.setColor(getRandColor(160, 200));

            // 画随机线
            for (int i = 0; i < 155; i++) {
                int x = RANDOM.nextInt(width - 1);
                int y = RANDOM.nextInt(height - 1);
                int xl = RANDOM.nextInt(6) + 1;
                int yl = RANDOM.nextInt(12) + 1;
                g.drawLine(x, y, x + xl, y + yl);
            }

            // 从另一方向画随机线
            for (int i = 0; i < 70; i++) {
                int x = RANDOM.nextInt(width - 1);
                int y = RANDOM.nextInt(height - 1);
                int xl = RANDOM.nextInt(12) + 1;
                int yl = RANDOM.nextInt(6) + 1;
                g.drawLine(x, y, x - xl, y - yl);
            }

            // 生成随机数,并将随机数字转换为字母
            final StringBuilder captchaValue = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char ch = (char) (RANDOM.nextInt(26) + 65);
                captchaValue.append(ch);
                g.setColor(new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110)));
                g.drawString(String.valueOf(ch), 15 * i + 10, 16);
            }
            g.dispose();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            byte[] data = out.toByteArray();

            String imageStr = Base64.getEncoder().encodeToString(data);
            String id = captchaIdGenerator.create();

            captchaDao.save(id, captchaValue.toString());

            log.info("CAPTCHA: '{}'", captchaValue.toString());

            return Captcha.builder()
                    .id(id)
                    .value(captchaValue.toString())
                    .image(imageStr)
                    .build();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + RANDOM.nextInt(bc - fc);
        int g = fc + RANDOM.nextInt(bc - fc);
        int b = fc + RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}

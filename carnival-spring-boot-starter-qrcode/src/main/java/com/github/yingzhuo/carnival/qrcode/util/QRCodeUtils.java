/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode.util;

import com.github.yingzhuo.carnival.qrcode.Logo;
import com.github.yingzhuo.carnival.qrcode.QRCodeGenerator;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.7.3
 */
public final class QRCodeUtils {

    private QRCodeUtils() {
    }

    public static BufferedImage generate(String content) {
        return SpringUtils.getBean(QRCodeGenerator.class).generate(content);
    }

    public static BufferedImage generate(String content, Logo logo) {
        return SpringUtils.getBean(QRCodeGenerator.class).generate(content, logo);
    }

    public static BufferedImage generate(String content, Logo logo, ErrorCorrectionLevel errorCorrectionLevel) {
        return SpringUtils.getBean(QRCodeGenerator.class).generate(content, logo, errorCorrectionLevel);
    }

    public static BufferedImage generate(String content, Logo logo, ErrorCorrectionLevel errorCorrectionLevel, int size) {
        return SpringUtils.getBean(QRCodeGenerator.class).generate(content, logo, errorCorrectionLevel, size);
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.6.16
 */
public final class QRCode {

    private QRCode() {
    }

    public static BufferedImage create(String text, int w) {
        return SpringUtils.getBean(QRCodeGenerator.class).create(text, w);
    }

    public static BufferedImage create(String text, int w, ErrorCorrectionLevel level) {
        return SpringUtils.getBean(QRCodeGenerator.class).create(text, w, level);
    }

}

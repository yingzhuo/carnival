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

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.6.16
 */
public interface QRCodeGenerator {

    public default BufferedImage create(String text, int width) {
        return create(text, width, ErrorCorrectionLevel.M);
    }

    public BufferedImage create(String text, int width, ErrorCorrectionLevel correctionLevel);

}

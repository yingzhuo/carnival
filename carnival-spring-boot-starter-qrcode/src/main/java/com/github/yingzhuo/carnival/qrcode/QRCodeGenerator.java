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
 * @since 1.7.3
 */
public interface QRCodeGenerator {

    public BufferedImage generate(String content);

    public BufferedImage generate(String content, Logo logo);

    public BufferedImage generate(String content, Logo logo, ErrorCorrectionLevel errorCorrectionLevel);

    public BufferedImage generate(String content, Logo logo, ErrorCorrectionLevel errorCorrectionLevel, int size);

}

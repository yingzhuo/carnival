/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.tool;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.OutputStream;

/**
 * @author 应卓
 */
public interface QRCodeCreator {

    public default void create(OutputStream outputStream, String content, int size) {
        create(outputStream, content, size, "png");
    }

    public default void create(OutputStream outputStream, String content, int size, String imageFormat) {
        create(outputStream, content, size, imageFormat, ErrorCorrectionLevel.L);
    }

    public void create(OutputStream outputStream, String content, int size, String imageFormat, ErrorCorrectionLevel errorCorrectionLevel);

}

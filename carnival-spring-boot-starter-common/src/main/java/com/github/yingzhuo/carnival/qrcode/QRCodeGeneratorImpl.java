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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.16
 */
public class QRCodeGeneratorImpl implements QRCodeGenerator {

    @Override
    public BufferedImage create(String text, int width, ErrorCorrectionLevel correctionLevel) {

        final Map<EncodeHintType, ErrorCorrectionLevel> configMap = new HashMap<>();
        configMap.put(EncodeHintType.ERROR_CORRECTION, correctionLevel);

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix byteMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, width, configMap);
            int matrixWidth = byteMatrix.getWidth();
            return new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        } catch (WriterException e) {
            throw new UncheckedIOException(e.getMessage(), new IOException(e.getCause()));
        }
    }

}

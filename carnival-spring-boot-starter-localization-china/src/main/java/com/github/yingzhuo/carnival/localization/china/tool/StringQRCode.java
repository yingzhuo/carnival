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
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.io.Serializable;

/**
 * @author 应卓
 */
@Getter
@Setter
public final class StringQRCode extends AbstractQRCode implements Serializable {

    private static final long serialVersionUID = 336472375152123976L;

    private String content;

    public static StringQRCode create(String content) {
        return StringQRCode.builder().content(content).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int size = 200;
        private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        private String format = "png";
        private String content = null;

        private Builder() {
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
            this.errorCorrectionLevel = errorCorrectionLevel;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public StringQRCode build() {
            val qrcode = new StringQRCode();
            qrcode.setSize(size);
            qrcode.setFormat(format);
            qrcode.setErrorCorrectionLevel(errorCorrectionLevel);
            return qrcode;
        }
    }
}

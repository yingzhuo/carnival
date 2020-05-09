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
import lombok.val;

import java.io.Serializable;

/**
 * @author 应卓
 */
public final class JsonQRCode extends AbstractQRCode implements Serializable {

    public JsonQRCode() {
        super();
    }

    public static JsonQRCode create() {
        return JsonQRCode.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "JsonQRCode()";
    }

    public static class Builder {

        private int size = 200;
        private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        private String format = "png";

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

        public JsonQRCode build() {
            val qrcode = new JsonQRCode();
            qrcode.setSize(size);
            qrcode.setFormat(format);
            qrcode.setErrorCorrectionLevel(errorCorrectionLevel);
            return qrcode;
        }
    }
}

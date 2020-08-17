/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode.props;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 * @since 1.7.3
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.qrcode")
public class Props {

    private boolean enabled = true;
    private Defaults defaults = new Defaults();

    @Getter
    @Setter
    public static class Defaults {
        private ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.H;
        private int margin = 1;
        private int size = 200;
    }

}

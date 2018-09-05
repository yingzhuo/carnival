/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.zxing.autoconfig;

import com.github.yingzhuo.carnival.zxing.QRCodeCreator;
import com.github.yingzhuo.carnival.zxing.mvc.QRCodeHandlerMethodReturnValueHandler;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(QRCodeWebAutoConfig.Props.class)
public class QRCodeWebAutoConfig implements WebMvcConfigurer {

    @Autowired
    private QRCodeCreator creator;

    @Autowired
    private Props props;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new QRCodeHandlerMethodReturnValueHandler(creator, props));
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.zxing.view")
    public static class Props {
        private int size = 600;
        private String imageFormat = "png";
        private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
    }

}

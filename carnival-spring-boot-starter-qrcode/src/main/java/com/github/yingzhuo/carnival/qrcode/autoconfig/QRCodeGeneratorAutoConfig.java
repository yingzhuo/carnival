/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode.autoconfig;

import com.github.yingzhuo.carnival.qrcode.QRCodeGenerator;
import com.github.yingzhuo.carnival.qrcode.QRCodeGeneratorImpl;
import com.github.yingzhuo.carnival.qrcode.props.Props;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.7.3
 */
@EnableConfigurationProperties(Props.class)
@ConditionalOnProperty(prefix = "carnival.qrcode", name = "enabled", havingValue = "true", matchIfMissing = true)
public class QRCodeGeneratorAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public QRCodeGenerator qrCodeGenerator(Props props) {
        final QRCodeGeneratorImpl bean = new QRCodeGeneratorImpl();
        bean.setDefaultErrorCorrectionLevel(props.getDefaults().getErrorCorrection());
        bean.setDefaultSize(props.getDefaults().getSize());
        bean.setDefaultMargin(props.getDefaults().getMargin());
        return bean;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.qrcode.QRCodeGenerator;
import com.github.yingzhuo.carnival.qrcode.QRCodeGeneratorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Lazy(false)
@ConditionalOnClass(name = {"com.google.zxing.qrcode.QRCodeWriter"})
public class QRCodeAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public QRCodeGenerator qrCodeGenerator() {
        return new QRCodeGeneratorImpl();
    }

}

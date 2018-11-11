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
import com.github.yingzhuo.carnival.zxing.impl.DefaultQRCodeCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class QRCodeAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public QRCodeCreator qrCodeCreator() {
        return new DefaultQRCodeCreator();
    }

}

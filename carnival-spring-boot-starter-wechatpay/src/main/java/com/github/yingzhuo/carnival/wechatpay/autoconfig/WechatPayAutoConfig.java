/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.wechatpay.autoconfig;

import com.github.yingzhuo.carnival.wechatpay.WechatPayService;
import com.github.yingzhuo.carnival.wechatpay.impl.DefaultWechatPayService;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(WechatPayAutoConfig.Props.class)
public class WechatPayAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WechatPayService wechatPayService(Props props) {
        val bean = new DefaultWechatPayService();
        bean.setDebugMode(props.isDebugMode());
        bean.setAppId(props.getAppId());
        bean.setMchId(props.getMchId());
        bean.setSecretKey(props.getSecretKey());
        bean.setNotifyUrl(props.getNotifyUrl());
        return bean;
    }


    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.wechat-pay")
    public static class Props implements InitializingBean {

        private String appId;
        private String mchId;
        private String secretKey;
        private String notifyUrl;
        private boolean debugMode = true;

        @Override
        public void afterPropertiesSet() {
            Assert.hasText(appId, "'appId' is blank or null");
            Assert.hasText(mchId, "'mchId' is blank or null");
            Assert.hasText(secretKey, "'secretKey' is blank or null");
            Assert.hasText(notifyUrl, "'notifyUrl' is blank or null");
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.alipay.autoconf;

import com.github.yingzhuo.carnival.alipay.AlipayService;
import com.github.yingzhuo.carnival.alipay.impl.DefaultAlipayService;
import com.github.yingzhuo.carnival.alipay.mvc.AlipayNotifyFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(AlipayAutoConfig.Props.class)
public class AlipayAutoConfig {

    @Bean
    public AlipayService alipayService(Props props) {
        val bean = new DefaultAlipayService();
        bean.setDebugMode(props.isDebugMode());
        bean.setAppId(props.getAppId());
        bean.setNotifyUrl(props.getNotifyUrl());
        bean.setPublicKey(props.getPublicKey());
        bean.setPrivateKey(props.getPrivateKey());
        bean.setProductCode(props.getProductCode());
        return bean;
    }


    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.alipay")
    @SuppressWarnings("ALL")
    public static class Props implements InitializingBean {

        private String appId;
        private String privateKey;
        private String publicKey;
        private String notifyUrl;
        private String productCode;
        private boolean debugMode = false;


        @Override
        public void afterPropertiesSet() {
            Assert.hasText(appId, "appId is blank or null.");
            Assert.hasText(privateKey, "privateKey is blank or null.");
            Assert.hasText(publicKey, "publicKey is blank or null.");
            Assert.hasText(notifyUrl, "notifyUrl is blank or null.");
            Assert.hasText(productCode, "productCode is blank or null.");
        }
    }

    @Getter
    @Setter
    public static class NotifyFilterProps {
        private String[] urlPatterns;
        private String name = AlipayNotifyFilter.class.getSimpleName();
        private int order = 0;
    }

}

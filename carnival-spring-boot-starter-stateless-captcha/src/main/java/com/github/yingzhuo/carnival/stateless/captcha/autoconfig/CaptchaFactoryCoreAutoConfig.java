/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.autoconfig;

import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaFactory;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaIdGenerator;
import com.github.yingzhuo.carnival.stateless.captcha.impl.DefaultCaptchaFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;

/**
 * @author 应卓
 */
@Lazy(false)
@EnableConfigurationProperties(CaptchaFactoryCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.stateless-captcha", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter({
        CaptchaFactoryBeanAutoConfig1.class,
        CaptchaFactoryBeanAutoConfig2.class
})
public class CaptchaFactoryCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaIdGenerator captchaIdGenerator() {
        return () -> UUID.randomUUID().toString();
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaFactory captchaFactory(CaptchaIdGenerator captchaIdGenerator, CaptchaDao dao, Props props) {
        final DefaultCaptchaFactory bean = new DefaultCaptchaFactory();
        bean.setCaptchaIdGenerator(captchaIdGenerator);
        bean.setCaptchaDao(dao);
        bean.setHeight(props.getHeight());
        bean.setWidth(props.getWidth());
        bean.setCharacters(props.getCharacters());
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.stateless-captcha")
    static class Props {
        private boolean enabled = true;
        private int width = 100;
        private int height = 18;
        private String characters = "absdegkmnopwxABSDEGKMNOPWX23456789";
    }

}

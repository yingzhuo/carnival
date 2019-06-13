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
import com.github.yingzhuo.carnival.stateless.captcha.impl.MapCaptchaDao;
import com.github.yingzhuo.carnival.stateless.captcha.impl.SimpleCaptchaFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(CaptchaFactoryAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.stateless-captcha", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CaptchaFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaFactory captchaFactory(CaptchaDao dao, Props props) {
        val factory = new SimpleCaptchaFactory(dao);
        factory.setHeight(props.getHeight());
        factory.setWidth(props.getWidth());
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaDao captchaDao() {
        return new MapCaptchaDao();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.stateless-captcha")
    static class Props {
        private boolean enabled = true;
        private int width = 100;
        private int height = 18;
    }

}

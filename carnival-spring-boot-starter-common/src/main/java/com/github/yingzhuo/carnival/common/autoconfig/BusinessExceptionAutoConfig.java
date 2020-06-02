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

import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.BusinessExceptionMap;
import com.github.yingzhuo.carnival.exception.business.MapBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.MessageSourceBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;

/**
 * @author 应卓
 */
@Slf4j
@Lazy(false)
@EnableConfigurationProperties({
        BusinessExceptionMap.class,
        BusinessExceptionAutoConfig.Props.class
})
@ConditionalOnProperty(prefix = "carnival.business-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BusinessExceptionAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props, BusinessExceptionMap envMap) {

        // i18n
        final I18n i18n = props.getI18n();
        String[] basenames = i18n.getBasenames();
        if (basenames != null && basenames.length != 0) {

            if (!envMap.isEmpty()) {
                log.warn("env properties 'business.exception.*' will be ignored.");
            }

            return new MessageSourceBusinessExceptionFactory(i18n.getBasenames(), i18n.getEncoding());
        }

        // no i18n
        return new MapBusinessExceptionFactory(envMap);
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props implements Serializable {
        private boolean enabled = true;
        private I18n i18n = new I18n();
    }

    @Getter
    @Setter
    static final class I18n implements Serializable {
        private String[] basenames = null;
        private String encoding = "UTF-8";
    }

}

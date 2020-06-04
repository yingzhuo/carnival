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
import com.github.yingzhuo.carnival.exception.business.MapBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.MessageSourceBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import static com.github.yingzhuo.carnival.exception.business.BusinessExceptionMaps.*;

/**
 * @author 应卓
 */
@Lazy(false)
@EnableConfigurationProperties({
        BusinessExceptionAutoConfig.Props.class,
        Map1.class,
        Map2.class,
        Map3.class
})
@ConditionalOnProperty(prefix = "carnival.business-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BusinessExceptionAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props, Map1 map1, Map2 map2, Map3 map3) {
        final I18n i18n = props.getI18n();
        final String[] basenames = i18n.getBasenames();

        if (basenames != null && basenames.length != 0) {
            return new MessageSourceBusinessExceptionFactory(i18n.getBasenames(), i18n.getEncoding());
        } else {
            return new MapBusinessExceptionFactory(
                    merge(map1,
                            map2,
                            map3)
            );
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props {
        private boolean enabled = true;
        private I18n i18n = new I18n();
    }

    @Getter
    @Setter
    static final class I18n {
        private String[] basenames = null;
        private String encoding = "UTF-8";
    }

}

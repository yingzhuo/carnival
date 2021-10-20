/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.autoconfig;

import com.github.yingzhuo.carnival.localization.china.tool.IdentityDescriptorConverter;
import com.github.yingzhuo.carnival.localization.china.tool.IdentityParser;
import com.github.yingzhuo.carnival.localization.china.tool.IdentityParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
class LocalizationChinaCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IdentityParser identityCardInfoParser() {
        return new IdentityParserImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IdentityDescriptorConverter identityDescriptorConverter() {
        return new IdentityDescriptorConverter(null);
    }

}

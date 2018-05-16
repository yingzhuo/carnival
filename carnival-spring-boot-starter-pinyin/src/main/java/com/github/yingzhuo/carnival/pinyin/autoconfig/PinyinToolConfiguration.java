/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.pinyin.autoconfig;

import com.github.yingzhuo.carnival.pinyin.PinyinTool;
import com.github.yingzhuo.carnival.pinyin.impl.SimplePinyinTool;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(PinyinToolConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.pinyin", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PinyinToolConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PinyinTool pinyinTool() {
        return new SimplePinyinTool();
    }

    @Data
    @ConfigurationProperties("carnival.pinyin")
    static class Props {
        private boolean enabled = true;
    }

}

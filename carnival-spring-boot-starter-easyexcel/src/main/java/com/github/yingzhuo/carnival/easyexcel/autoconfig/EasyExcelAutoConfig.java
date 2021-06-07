/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.autoconfig;

import com.github.yingzhuo.carnival.easyexcel.ExcelReader;
import com.github.yingzhuo.carnival.easyexcel.core.DefaultExcelReader;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.9.2
 */
@EnableConfigurationProperties(EasyExcelAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.easyexcel", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EasyExcelAutoConfig {

    @Bean
    public ExcelReader excelReader() {
        return new DefaultExcelReader();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.easyexcel")
    static class Props {
        private boolean enabled = true;
    }

}

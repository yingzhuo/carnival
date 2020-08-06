/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stringtemplate.autoconfig;

import com.github.yingzhuo.carnival.stringtemplate.impl.MustacheStringTemplateBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.33
 */
@ConditionalOnClass(name = "com.github.mustachejava.Mustache")
public class MustacheStringTemplateBeanAutoConfig {

    @Bean
    public MustacheStringTemplateBean mustacheStringTemplateBean() {
        return new MustacheStringTemplateBean();
    }

}

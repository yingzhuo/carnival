/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.document.autoconfig;

import com.github.yingzhuo.carnival.document.DocumentConfigurer;
import com.github.yingzhuo.carnival.document.core.DocumentPageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.6.11
 */
@Lazy(false)
@ConditionalOnWebApplication
public class DocumentAutoConfig {

    @Autowired(required = false)
    private DocumentConfigurer configurer = new DocumentConfigurer() {
    };

    @Bean
    public FilterRegistrationBean<DocumentPageFilter> documentFilter() {
        String rootPath = configurer.rootPath();

        final FilterRegistrationBean<DocumentPageFilter> bean = new FilterRegistrationBean<>(
                new DocumentPageFilter(
                        rootPath,
                        configurer.documentMap().getDocumentResources(),
                        configurer.charset()
                )
        );

        String pattern = rootPath.endsWith("/*") ? rootPath : rootPath + "/*";

        bean.addUrlPatterns(pattern);
        bean.setName(DocumentPageFilter.class.getSimpleName());
        return bean;
    }

}

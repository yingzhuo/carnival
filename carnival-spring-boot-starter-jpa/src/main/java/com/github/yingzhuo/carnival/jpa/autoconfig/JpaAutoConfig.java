/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.autoconfig;

import com.github.yingzhuo.carnival.jpa.converter.NormalizedKeywordAnnotationFormatterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 * @since 1.10.22
 */
class JpaAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new NormalizedKeywordAnnotationFormatterFactory());
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.support.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import org.springframework.beans.factory.FactoryBean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.10.20
 */
public class HandlebarsFactoryBean implements FactoryBean<Handlebars> {

    private final String prefix;
    private final String suffix;
    private final Charset charset;

    public HandlebarsFactoryBean(String prefix, String suffix) {
        this(prefix, suffix, StandardCharsets.UTF_8);
    }

    public HandlebarsFactoryBean(String prefix, String suffix, Charset charset) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.charset = charset;
    }

    @Override
    public Handlebars getObject() {
        final ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);
        loader.setCharset(charset);
        return new Handlebars(loader);
    }

    @Override
    public Class<?> getObjectType() {
        return Handlebars.class;
    }

}

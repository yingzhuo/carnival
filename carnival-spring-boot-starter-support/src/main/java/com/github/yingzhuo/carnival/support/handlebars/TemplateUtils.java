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

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Template;
import com.github.yingzhuo.carnival.collection.Dict;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @see Templates
 * @see TemplatesFactoryBean
 * @since 1.10.20
 */
public final class TemplateUtils {

    private TemplateUtils() {
    }

    public static String apply(String location, String model) {
        try {
            Template template = SpringUtils.getBean(Templates.class).map.get(location);
            return template.apply(Context.newBuilder(model).build());
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static String apply(String location, Dict context) {
        try {
            Template template = SpringUtils.getBean(Templates.class).map.get(location);
            return template.apply(Context.newBuilder(null).combine(context).build());
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

}

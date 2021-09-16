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
import org.springframework.beans.factory.FactoryBean;

import java.util.Collections;

/**
 * @author 应卓
 * @since 1.10.20
 */
public class TemplatesFactoryBean implements FactoryBean<Templates> {

    private final Handlebars handlebars;
    private final String[] locations;

    public TemplatesFactoryBean(Handlebars handlebars, String... locations) {
        this.handlebars = handlebars;
        this.locations = locations;
    }

    @Override
    public Templates getObject() throws Exception {
        final Templates templates = new Templates();
        for (String location : locations) {
            templates.map.put(location, handlebars.compile(location));
        }
        templates.map = Collections.unmodifiableMap(templates.map);
        return templates;
    }

    @Override
    public Class<?> getObjectType() {
        return Templates.class;
    }

}

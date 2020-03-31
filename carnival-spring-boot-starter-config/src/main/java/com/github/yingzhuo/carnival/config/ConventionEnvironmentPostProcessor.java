/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config;

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;
import lombok.var;
import org.springframework.boot.system.ApplicationHome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final List<String> DEFAULT_PREFIX = new ArrayList<>(14);

    static {
        var home = "file:" + new ApplicationHome().getDir();
        if (!home.endsWith("/")) {
            home += "/";
        }

        DEFAULT_PREFIX.add(home + "config/property");
        DEFAULT_PREFIX.add(home + "config/property-source");
        DEFAULT_PREFIX.add(home + "property");
        DEFAULT_PREFIX.add(home + "property-source");
        DEFAULT_PREFIX.add("file:config/property");
        DEFAULT_PREFIX.add("file:config/property-source");
        DEFAULT_PREFIX.add("file:property");
        DEFAULT_PREFIX.add("file:property-source");
        DEFAULT_PREFIX.add("classpath:config/property");
        DEFAULT_PREFIX.add("classpath:config/property-source");
        DEFAULT_PREFIX.add("classpath:property");
        DEFAULT_PREFIX.add("classpath:property-source");
        DEFAULT_PREFIX.add("classpath:META-INF/property");
        DEFAULT_PREFIX.add("classpath:META-INF/property-source");
    }

    private static final String NAME = "property-source";

    public ConventionEnvironmentPostProcessor() {
        super(DEFAULT_PREFIX.toArray(new String[0]), NAME);
    }

}

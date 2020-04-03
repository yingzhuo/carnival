/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.env;

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;
import lombok.var;
import org.springframework.boot.system.ApplicationHome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class DataSourceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final List<String> DEFAULT_PREFIX = new ArrayList<>(7);

    static {
        var home = "file:" + new ApplicationHome().getDir().getPath();
        if (!home.endsWith("/")) {
            home += "/";
        }

        DEFAULT_PREFIX.add(home + "config/datasource");
        DEFAULT_PREFIX.add(home + "datasource");
        DEFAULT_PREFIX.add("file:config/datasource");
        DEFAULT_PREFIX.add("file:datasource");
        DEFAULT_PREFIX.add("classpath:config/datasource");
        DEFAULT_PREFIX.add("classpath:datasource");
        DEFAULT_PREFIX.add("classpath:META-INF/datasource");
    }

    private static final String NAME = "datasource";

    public DataSourceConventionEnvironmentPostProcessor() {
        super(DEFAULT_PREFIX.toArray(new String[0]), NAME);
    }

}

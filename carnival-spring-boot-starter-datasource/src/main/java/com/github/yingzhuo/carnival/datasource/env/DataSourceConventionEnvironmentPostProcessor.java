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

import com.github.yingzhuo.springboot.env.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.springboot.env.util.JarDir;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class DataSourceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public DataSourceConventionEnvironmentPostProcessor() {
        super("datasource", new String[]{
                JarDir.of().getDirAsResourceLocation("config/datasource"),
                JarDir.of().getDirAsResourceLocation("datasource"),
                "file:config/datasource",
                "file:datasource",
                "classpath:config/datasource",
                "classpath:datasource",
                "classpath:META-INF/datasource",
        });
    }

}

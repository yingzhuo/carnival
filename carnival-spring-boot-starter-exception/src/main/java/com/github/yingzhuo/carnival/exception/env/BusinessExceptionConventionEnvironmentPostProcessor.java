/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.env;

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;
import org.springframework.boot.system.ApplicationHome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class BusinessExceptionConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final List<String> DEFAULT_PREFIX = new ArrayList<>(6);

    static {
        final String deploymentDir = "file:" + new ApplicationHome().getDir() + "/";
        DEFAULT_PREFIX.add(deploymentDir + "business-exception");
        DEFAULT_PREFIX.add("file:config/business-exception");
        DEFAULT_PREFIX.add("file:business-exception");
        DEFAULT_PREFIX.add("classpath:config/business-exception");
        DEFAULT_PREFIX.add("classpath:business-exception");
        DEFAULT_PREFIX.add("classpath:META-INF/business-exception");
    }

    private static final String NAME = "business-exception";

    public BusinessExceptionConventionEnvironmentPostProcessor() {
        super(DEFAULT_PREFIX.toArray(new String[0]), NAME);
    }

}

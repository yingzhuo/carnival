/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.0.0
 */
public class ApplicationContextProvider implements Ordered, ApplicationContextAware, EnvironmentAware, ApplicationRunner, CommandLineRunner {

    public static final ApplicationContextProvider INSTANCE = new ApplicationContextProvider();

    private ApplicationContextProvider() {
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        SpringUtils.AC = ctx;
    }

    @Override
    public void setEnvironment(Environment env) {
        SpringUtils.ENV = env;
    }

    @Override
    public void run(ApplicationArguments args) {
        SpringUtils.APP_ARGS = args;
    }

    @Override
    public void run(String... args) {
        if (args == null) {
            args = new String[0];
        }
        SpringUtils.CMD_ARGS = Arrays.asList(args);
    }

}

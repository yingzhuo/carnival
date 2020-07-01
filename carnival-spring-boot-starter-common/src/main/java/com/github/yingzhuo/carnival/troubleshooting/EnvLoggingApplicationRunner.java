/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.troubleshooting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author 应卓
 * @since 1.6.24
 */
@Slf4j(topic = "troubleshooting")
public class EnvLoggingApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments ignored) {

        if (!log.isDebugEnabled()) {
            return;
        }

        log.debug(StringUtils.repeat('-', 120));
        log.debug("env(s):");

        final Map<String, String> env = new TreeMap<>(System.getenv());
        for (String name : env.keySet()) {
            String value = env.get(name);

            if (hide(name)) {
                value = "[****]";
            }

            log.debug("\t\t{} = {}", name, value);
        }

        log.debug(StringUtils.repeat('-', 120));
    }

    private boolean hide(String name) {
        return StringUtils.containsIgnoreCase(name, "token") ||
                StringUtils.containsIgnoreCase(name, "password") ||
                StringUtils.containsIgnoreCase(name, "secret");
    }
}

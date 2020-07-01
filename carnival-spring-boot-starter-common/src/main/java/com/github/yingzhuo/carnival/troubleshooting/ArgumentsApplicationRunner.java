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
import org.springframework.boot.CommandLineRunner;

/**
 * @author 应卓
 * @since 1.6.24
 */
@Slf4j(topic = "troubleshooting")
public class ArgumentsApplicationRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {

        if (!log.isDebugEnabled() || args.length == 0) {
            return;
        }

        log.debug(StringUtils.repeat('-', 120));
        log.debug("[Args]:");

        for (String arg : args) {
            log.debug("\t\t{}", arg);
        }

        log.debug(StringUtils.repeat('-', 120));
    }

}

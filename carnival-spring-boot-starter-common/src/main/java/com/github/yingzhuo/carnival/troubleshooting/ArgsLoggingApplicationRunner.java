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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.6.24
 */
@Slf4j(topic = "troubleshooting")
public class ArgsLoggingApplicationRunner extends AbstractTroubleshootingBean implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        if (!log.isDebugEnabled()) {
            return;
        }

        final Set<String> optionNames = args.getOptionNames()
                .stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        final List<String> nonOptionsArgs = args.getNonOptionArgs()
                .stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        if (optionNames.isEmpty() && nonOptionsArgs.isEmpty()) {
            return;
        }

        log.debug(StringUtils.repeat('-', 120));

        if (!optionNames.isEmpty()) {
            log.debug("[Args (Option)]:");
            for (String optionName : optionNames) {
                final List<String> optionValues = args.getOptionValues(optionName);
                if (optionValues == null || optionValues.isEmpty()) {
                    log.debug("\t\t{}", optionName);
                } else {
                    for (String optionValue : optionValues) {
                        if (isHiden(optionName)) {
                            optionValue = HIDDEN_PWD;
                        }
                        log.debug("\t\t{} = {}", optionName, optionValue);
                    }
                }
            }
        }

        if (!nonOptionsArgs.isEmpty()) {
            log.debug("[Args (Non-Option)]");
            for (String nonOptionArg : nonOptionsArgs) {
                log.debug("\t\t{}", nonOptionArg);
            }
        }

        log.debug(StringUtils.repeat('-', 120));
    }

}

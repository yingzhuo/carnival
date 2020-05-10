/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

/**
 * @author 应卓
 * @since 1.6.6
 */
public final class Debugger implements EnvironmentAware {

    public static final Debugger INSTANCE = new Debugger();
    private static final Logger log = LoggerFactory.getLogger("carnival.debugger");
    private static boolean enabled;

    private Debugger() {
    }

    public static void debug(String format, Object... args) {
        if (enabled && log.isDebugEnabled()) {
            if (!format.startsWith("[Carnival DebugMode]")) {
                format = "[Carnival DebugMode]" + format;
            }
            log.debug(format, args);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void setEnvironment(Environment environment) {
        Debugger.enabled = environment.acceptsProfiles(Profiles.of("debug"));
    }

}

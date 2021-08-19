/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 应卓
 * @since 1.10.10
 */
public class ConfigurableLogger {

    public static Builder builder() {
        return new Builder();
    }

    private final Logger log;
    private final LoggerLevel level;

    private ConfigurableLogger(String loggerName, LoggerLevel level) {
        this.log = LoggerFactory.getLogger(loggerName);
        this.level = level != null ? level : LoggerLevel.DEBUG;
    }

    public boolean isEnabled() {
        switch (level) {
            case TRACE:
                return log.isTraceEnabled();
            case DEBUG:
                return log.isDebugEnabled();
            case INFO:
                return log.isInfoEnabled();
            case WARN:
                return log.isWarnEnabled();
            case ERROR:
                return log.isErrorEnabled();
            default:
                return false;
        }
    }

    public void log(String format, Object... args) {
        if (isEnabled()) {
            switch (level) {
                case TRACE:
                    trace(format, args);
                case DEBUG:
                    debug(format, args);
                case INFO:
                    info(format, args);
                case WARN:
                    warn(format, args);
                case ERROR:
                    error(format, args);
                default:
                    // nop
            }
        }
    }

    private void trace(String format, Object... args) {
        log.trace(format, args);
    }

    private void debug(String format, Object... args) {
        log.debug(format, args);
    }

    private void info(String format, Object... args) {
        log.info(format, args);
    }

    private void warn(String format, Object... args) {
        log.warn(format, args);
    }

    private void error(String format, Object... args) {
        log.warn(format, args);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static class Builder {
        private Builder() {
        }

        private LoggerLevel level = LoggerLevel.DEBUG;
        private String loggerName = ConfigurableLogger.class.getName();

        public Builder level(LoggerLevel level) {
            this.level = level;
            return this;
        }

        public Builder name(String loggerName) {
            this.loggerName = loggerName;
            return this;
        }

        public Builder name(Class<?> loggerName) {
            this.loggerName = loggerName.getName();
            return this;
        }

        public ConfigurableLogger build() {
            return new ConfigurableLogger(loggerName, level);
        }
    }

}

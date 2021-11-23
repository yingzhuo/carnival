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
import org.springframework.boot.logging.LogLevel;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.10
 */
public final class ConfigurableLogger {

    private final Logger log;
    private final LogLevel level;

    private ConfigurableLogger(String loggerName, LogLevel level) {
        this.level = Objects.requireNonNull(level);
        this.log = LoggerFactory.getLogger(Objects.requireNonNull(loggerName));
    }

    public static Builder builder() {
        return new Builder();
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
            case FATAL:
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
                    break;
                case DEBUG:
                    debug(format, args);
                    break;
                case INFO:
                    info(format, args);
                    break;
                case WARN:
                    warn(format, args);
                    break;
                case FATAL:
                case ERROR:
                    error(format, args);
                    break;
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

    public final static class Builder {

        private LogLevel level = LogLevel.DEBUG;
        private String loggerName = ConfigurableLogger.class.getName();

        private Builder() {
        }

        public Builder level(LogLevel level) {
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

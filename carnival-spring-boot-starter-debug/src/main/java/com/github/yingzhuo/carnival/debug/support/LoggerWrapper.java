/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.debug.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author 应卓
 */
public class LoggerWrapper {

    private final Logger log;
    private LogLevel logLevel;

    public LoggerWrapper(LogLevel logLevel, String loggerName) {
        this.logLevel = Objects.requireNonNull(logLevel);
        this.log = Objects.requireNonNull(LoggerFactory.getLogger(loggerName));
    }

    public LoggerWrapper(LogLevel logLevel, Class<?> loggerClass) {
        this.logLevel = Objects.requireNonNull(logLevel);
        this.log = Objects.requireNonNull(LoggerFactory.getLogger(loggerClass));
    }

    public void doLog(String format, Object... args) {
        switch (logLevel) {
            case OFF:
                break;
            case TRACE:
                log.trace(format, args);
                break;
            case DEBUG:
                log.debug(format, args);
                break;
            case INFO:
                log.info(format, args);
                break;
            case WARN:
                log.warn(format, args);
                break;
            case ERROR:
                log.error(format, args);
                break;
        }
    }

}

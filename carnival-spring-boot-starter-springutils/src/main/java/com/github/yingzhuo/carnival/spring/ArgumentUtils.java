package com.github.yingzhuo.carnival.spring;

import org.springframework.boot.ApplicationArguments;

import java.util.List;

import static com.github.yingzhuo.carnival.spring.SpringUtils.APP_ARGS;
import static com.github.yingzhuo.carnival.spring.SpringUtils.CMD_ARGS;

/**
 * @author 应卓
 */
final public class ArgumentUtils {

    private ArgumentUtils() {
    }

    public static ApplicationArguments getApplicationArguments() {
        return APP_ARGS;
    }

    public static List<String> getCommandArguments() {
        return CMD_ARGS;
    }

}

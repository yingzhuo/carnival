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

import org.springframework.boot.SpringApplication;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.8.0
 */
public final class ShutdownUtils {

    private ShutdownUtils() {
    }

    public static void initiate(int returnCode) {
        SpringApplication.exit(SpringUtils.getApplicationContext(), () -> returnCode);
    }

}

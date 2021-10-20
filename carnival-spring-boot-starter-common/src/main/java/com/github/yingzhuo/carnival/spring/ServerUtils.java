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

/**
 * @author 应卓
 * @since 1.10.31
 */
public final class ServerUtils {

    private ServerUtils() {
    }

    public static int getLocalServerPort() {
        try {
            return Integer.parseInt(EnvironmentUtils.getPropertyValue("local.server.port"));
        } catch (Exception e) {
            return -1;
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author 应卓
 * @since 1.4.2
 */
public final class SocketUtils {

    private SocketUtils() {
    }

    public static boolean isReachable(String address, int port) {
        return isReachable(address, port, 0);
    }

    public static boolean isReachable(String address, int port, int timeoutInMilliseconds) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), timeoutInMilliseconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

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

import com.google.common.net.HostAndPort;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author 应卓
 * @since 1.4.2
 */
public final class SocketUtils {

    public static boolean isReachable(String address, int port) {
        return isReachable(address, port, 0);
    }

    public static boolean isReachable(String address, int port, int timeoutInMilliseconds) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), timeoutInMilliseconds);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isReachable(HostAndPort hostAndPort) {
        return isReachable(hostAndPort, 0);
    }

    public static boolean isReachable(HostAndPort hostAndPort, int timeoutInMilliseconds) {
        return isReachable(hostAndPort.getHost(), hostAndPort.getPort(), timeoutInMilliseconds);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SocketUtils() {
    }
}

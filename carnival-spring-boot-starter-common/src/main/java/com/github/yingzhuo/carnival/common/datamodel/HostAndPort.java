/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.3.1
 */
public final class HostAndPort implements java.io.Serializable {

    private static final int NO_PORT = -1;
    private final String host;
    private final int port;
    private final boolean hasBracketlessColons;

    private HostAndPort(String host, int port, boolean hasBracketlessColons) {
        this.host = host;
        this.port = port;
        this.hasBracketlessColons = hasBracketlessColons;
    }

    public String getHost() {
        return host;
    }

    public boolean hasPort() {
        return port >= 0;
    }

    public int getPort() {
        if (!hasPort()) {
            throw new IllegalArgumentException();
        }
        return port;
    }

    public int getPortOrDefault(int defaultPort) {
        return hasPort() ? port : defaultPort;
    }

    public static HostAndPort fromParts(String host, int port) {
        checkArgument(isValidPort(port), "Port out of range: %s", port);
        HostAndPort parsedHost = fromString(host);
        checkArgument(!parsedHost.hasPort(), "Host has a port: %s", host);
        return new HostAndPort(parsedHost.host, port, parsedHost.hasBracketlessColons);
    }

    public static HostAndPort fromHost(String host) {
        HostAndPort parsedHost = fromString(host);
        checkArgument(!parsedHost.hasPort(), "Host has a port: %s", host);
        return parsedHost;
    }

    public static HostAndPort fromString(String hostPortString) {
        checkNotNull(hostPortString);
        String host;
        String portString = null;
        boolean hasBracketlessColons = false;

        if (hostPortString.startsWith("[")) {
            String[] hostAndPort = getHostAndPortFromBracketedHost(hostPortString);
            host = hostAndPort[0];
            portString = hostAndPort[1];
        } else {
            int colonPos = hostPortString.indexOf(':');
            if (colonPos >= 0 && hostPortString.indexOf(':', colonPos + 1) == -1) {
                // Exactly 1 colon. Split into host:port.
                host = hostPortString.substring(0, colonPos);
                portString = hostPortString.substring(colonPos + 1);
            } else {
                // 0 or 2+ colons. Bare hostname or IPv6 literal.
                host = hostPortString;
                hasBracketlessColons = (colonPos >= 0);
            }
        }

        int port = NO_PORT;
        if (!isNullOrEmpty(portString)) {
            // Try to parse the whole port string as a number.
            // JDK7 accepts leading plus signs. We don't want to.
            checkArgument(!portString.startsWith("+"), "Unparseable port number: %s", hostPortString);
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unparseable port number: " + hostPortString);
            }
            checkArgument(isValidPort(port), "Port number out of range: %s", hostPortString);
        }

        return new HostAndPort(host, port, hasBracketlessColons);
    }

    private static String[] getHostAndPortFromBracketedHost(String hostPortString) {
        int colonIndex = 0;
        int closeBracketIndex = 0;
        checkArgument(
                hostPortString.charAt(0) == '[',
                "Bracketed host-port string must start with a bracket: %s",
                hostPortString);
        colonIndex = hostPortString.indexOf(':');
        closeBracketIndex = hostPortString.lastIndexOf(']');
        checkArgument(
                colonIndex > -1 && closeBracketIndex > colonIndex,
                "Invalid bracketed host/port: %s",
                hostPortString);

        String host = hostPortString.substring(1, closeBracketIndex);
        if (closeBracketIndex + 1 == hostPortString.length()) {
            return new String[]{host, ""};
        } else {
            checkArgument(
                    hostPortString.charAt(closeBracketIndex + 1) == ':',
                    "Only a colon may follow a close bracket: %s",
                    hostPortString);
            for (int i = closeBracketIndex + 2; i < hostPortString.length(); ++i) {
                checkArgument(
                        Character.isDigit(hostPortString.charAt(i)),
                        "Port must be numeric: %s",
                        hostPortString);
            }
            return new String[]{host, hostPortString.substring(closeBracketIndex + 2)};
        }
    }

    public HostAndPort withDefaultPort(int defaultPort) {
        checkArgument(isValidPort(defaultPort));
        if (hasPort()) {
            return this;
        }
        return new HostAndPort(host, defaultPort, hasBracketlessColons);
    }

    public HostAndPort requireBracketsForIPv6() {
        checkArgument(!hasBracketlessColons, "Possible bracketless IPv6 literal: %s", host);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostAndPort that = (HostAndPort) o;
        return port == that.port &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public String toString() {
        // "[]:12345" requires 8 extra bytes.
        StringBuilder builder = new StringBuilder(host.length() + 8);
        if (host.indexOf(':') >= 0) {
            builder.append('[').append(host).append(']');
        } else {
            builder.append(host);
        }
        if (hasPort()) {
            builder.append(':').append(port);
        }
        return builder.toString();
    }

    private static boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    private static void checkArgument(boolean state, String format, Object... args) {
        if (!state) {
            final String msg = String.format(format, args);
            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkArgument(boolean state) {
        if (!state) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private static final long serialVersionUID = 0;
}

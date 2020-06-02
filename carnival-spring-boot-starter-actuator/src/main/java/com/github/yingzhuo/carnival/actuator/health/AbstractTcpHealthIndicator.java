/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.health;

import com.github.yingzhuo.carnival.common.util.SocketUtils;
import com.google.common.net.HostAndPort;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.13
 */
public abstract class AbstractTcpHealthIndicator extends AbstractHealthIndicator {

    private final Logic logic;
    private final Duration timeout;
    private final Set<HostAndPort> targets;

    public AbstractTcpHealthIndicator(Duration timeout, HostAndPort... targets) {
        this(null, timeout, targets);
    }

    public AbstractTcpHealthIndicator(Logic logic, Duration timeout, HostAndPort... targets) {
        this.timeout = timeout;
        this.logic = logic != null ? logic : Logic.ALL;
        this.targets = new HashSet<>(Arrays.asList(targets));
    }

    @Override
    protected final void doHealthCheck(Health.Builder builder) {
        final boolean ok;

        if (logic == Logic.ALL) {
            ok = targets.stream().allMatch(hostAndPort -> SocketUtils.isReachable(hostAndPort, (int) timeout.getSeconds()));
        } else {
            ok = targets.stream().anyMatch(hostAndPort -> SocketUtils.isReachable(hostAndPort, (int) timeout.getSeconds()));
        }

        if (ok) {
            builder.up();
        } else {
            builder.down();
        }
    }

    public enum Logic {
        ALL, ANY
    }

}

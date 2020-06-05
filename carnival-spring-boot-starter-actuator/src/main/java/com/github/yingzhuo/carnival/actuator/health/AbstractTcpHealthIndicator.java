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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.13
 */
public abstract class AbstractTcpHealthIndicator extends AbstractHealthIndicator implements InitializingBean {

    private HostAndPort target;
    private Duration timeout = Duration.ofSeconds(2L);

    public AbstractTcpHealthIndicator() {
    }

    @Override
    protected final void doHealthCheck(Health.Builder builder) {
        final boolean ok = SocketUtils.isReachable(target, (int) timeout.getSeconds());

        if (ok) {
            builder.up();
        } else {
            builder.down();
        }
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(target, "target is null");
    }

    public void setTarget(HostAndPort target) {
        this.target = target;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

}

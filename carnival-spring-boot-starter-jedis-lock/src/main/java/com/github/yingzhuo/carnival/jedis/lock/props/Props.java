/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.lock.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.distributed-lock")
public class Props implements Serializable {

    private boolean enabled = true;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeToLive = Duration.ofSeconds(10);

    private RedisKey redisKey = new RedisKey();

    private WaitAndRun waitAndRun = new WaitAndRun();

    @Getter
    @Setter
    public static class RedisKey implements Serializable {
        private String prefix = "carnival-distributed-lock-";
        private String suffix = "";
    }

    @Getter
    @Setter
    public static class WaitAndRun implements Serializable {
        private Duration sleep = Duration.ofMillis(100L);
    }

}

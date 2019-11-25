/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.props;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.3.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.curator.retry")
public class RetryProps implements Serializable {

    private int baseSleepTimeMs = 1000;
    private int maxRetries = 3;
    private int maxSleepMs = Integer.MAX_VALUE;

    public RetryPolicy toRetryPolicy() {
        return new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries, maxSleepMs);
    }
}

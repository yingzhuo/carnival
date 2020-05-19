/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.lock;

import com.github.yingzhuo.carnival.jedis.lock.props.Props;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Lazy(false)
@EnableConfigurationProperties(Props.class)
public class JedisLockCoreAutoConfig implements ApplicationRunner {

    @Autowired
    private Props props;

    @Autowired(required = false)
    private DistributedLockExceptionThrower exceptionThrower;

    @Override
    public void run(ApplicationArguments args) {
        // 初始化工具类静态部分
        DistributedLock.enabled = props.isEnabled();
        DistributedLock.prefix = props.getRedisKey().getPrefix();
        DistributedLock.suffix = props.getRedisKey().getSuffix();
        DistributedLock.springId = SpringUtils.getSpringId();
        DistributedLock.timeToLive = props.getTimeToLive().toMillis();
        DistributedLock.waitAndRunSleep = props.getWaitAndRun().getSleep().toMillis();
        DistributedLock.exceptionThrower = exceptionThrower;
    }

}

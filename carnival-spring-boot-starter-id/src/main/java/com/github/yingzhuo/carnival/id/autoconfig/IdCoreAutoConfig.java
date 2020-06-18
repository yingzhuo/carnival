/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.autoconfig;

import com.github.yingzhuo.carnival.id.Algorithm;
import com.github.yingzhuo.carnival.id.IdGenerator;
import com.github.yingzhuo.carnival.id.impl.SnowflakeLongIdGenerator;
import com.github.yingzhuo.carnival.id.impl.SnowflakeStringIdGenerator;
import com.github.yingzhuo.carnival.id.impl.UUIDGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
@EnableConfigurationProperties(IdCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.id", name = "enabled", havingValue = "true", matchIfMissing = true)
public class IdCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator<?> idGenerator(Props props) {
        switch (props.getAlgorithm()) {
            case SNOWFLAKE:
            case SNOWFLAKE_STRING:

                var workerId = props.getSnowflake().getWorkerId();
                var dataCenterId = props.getSnowflake().getDataCenterId();

                if (props.getAlgorithm() == Algorithm.SNOWFLAKE) {
                    return new SnowflakeLongIdGenerator(workerId, dataCenterId);
                } else {
                    return new SnowflakeStringIdGenerator(workerId, dataCenterId, props.getSnowflake().getLength(), props.getSnowflake().getPadCharacter());
                }
            case UUID_32:
                return new UUIDGenerator(true);
            case UUID_36:
                return new UUIDGenerator(false);
            default:
                throw new AssertionError();
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.id")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.SNOWFLAKE;
        private Snowflake snowflake = new Snowflake();
    }

    @Getter
    @Setter
    static class Snowflake {
        private long workerId = 0L;
        private long dataCenterId = 0L;
        private int length = -1;
        private char padCharacter = '0';
    }
}

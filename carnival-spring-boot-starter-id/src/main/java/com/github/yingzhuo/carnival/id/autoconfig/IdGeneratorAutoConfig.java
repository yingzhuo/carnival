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
import com.github.yingzhuo.carnival.id.impl.UUID32IdGenerator;
import com.github.yingzhuo.carnival.id.impl.UUID36IdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(IdGeneratorAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.id", name = "enabled", havingValue = "true", matchIfMissing = true)
public class IdGeneratorAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator<?> idGenerator(Props props) {

        switch (props.getAlgorithm()) {
            case UUID_32:
                return new UUID32IdGenerator();
            case UUID_36:
                return new UUID36IdGenerator();
            case SNOWFLAKE:
            case SNOWFLAKE_STRING:

                // 本地配置
                var workerId = props.getSnowflake().getWorkerId();
                var dataCenterId = props.getSnowflake().getDataCenterId();

                // 环境变量
                val envWorkerId = getEnvWorkId();
                val envDataCenterId = getEnvDataCenterId();

                if (envWorkerId != -1) {
                    workerId = envWorkerId;
                }

                if (envDataCenterId != -1) {
                    dataCenterId = envDataCenterId;
                }

                log.debug("SNOWFLAKE_WORKER_ID: {}", workerId);
                log.debug("SNOWFLAKE_DATA_CENTER_ID: {}", dataCenterId);

                if (props.getAlgorithm() == Algorithm.SNOWFLAKE) {
                    return new SnowflakeLongIdGenerator(workerId, dataCenterId);
                } else {
                    return new SnowflakeStringIdGenerator(workerId, dataCenterId, props.getSnowflake().getLength(), props.getSnowflake().getPadCharacter());
                }

            default:
                throw new AssertionError();
        }
    }

    private long getEnvWorkId() {
        try {
            val value = Long.parseLong(System.getenv("CARNIVAL_SNOWFLAKE_WORKER_ID"));
            log.trace("CARNIVAL_SNOWFLAKE_WORKER_ID (environment): {}", value);
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private long getEnvDataCenterId() {
        try {
            val value = Long.parseLong(System.getenv("CARNIVAL_SNOWFLAKE_DATA_CENTER_ID"));
            log.trace("CARNIVAL_SNOWFLAKE_DATA_CENTER_ID (environment): {}", value);
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.id")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.SNOWFLAKE;
        private Snowflake snowflake = new Snowflake();

        @Getter
        @Setter
        static class Snowflake {
            private long workerId = 0L;
            private long dataCenterId = 0L;
            private int length = 32;
            private char padCharacter = '0';
        }
    }

}

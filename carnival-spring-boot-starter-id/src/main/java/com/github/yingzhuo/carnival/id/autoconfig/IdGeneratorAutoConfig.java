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
import com.github.yingzhuo.carnival.id.impl.SnowflakeIdGenerator;
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

    private final Props props;

    public IdGeneratorAutoConfig(Props props) {
        this.props = props;
    }

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator<?> stringIdGenerator() {

        switch (props.getAlgorithm()) {
            case UUID_32:
                return new UUID32IdGenerator();
            case UUID_36:
                return new UUID36IdGenerator();
            case SNOWFLAKE:
                var workerId = props.getSnowflake().getWorkerId();
                var dataCenterId = props.getSnowflake().getDataCenterId();
                val envWorkerId = getEnvWorkId();
                val envDataCenterId = getEnvDataCenterId();

                if (envWorkerId != -1) {
                    workerId = envWorkerId;
                }

                if (envDataCenterId != -1) {
                    dataCenterId = envDataCenterId;
                }

                log.info("SNOWFLAKE_WORKER_ID: {}", workerId);
                log.info("SNOWFLAKE_DATA_CENTER_ID: {}", dataCenterId);

                return new SnowflakeIdGenerator(workerId, dataCenterId);
            default:
                throw new UnsupportedOperationException();      // 程序不会运行到此处
        }
    }

    private long getEnvWorkId() {
        try {
            return Long.parseLong(System.getenv("CARNIVAL_SNOWFLAKE_WORKER_ID"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private long getEnvDataCenterId() {
        try {
            return Long.parseLong(System.getenv("CARNIVAL_SNOWFLAKE_DATA_CENTER_ID"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.id")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.SNOWFLAKE;
        private Snowflake snowflake = new Snowflake();

        @Getter
        @Setter
        static class Snowflake {
            private long workerId = 0L;
            private long dataCenterId = 0L;
        }
    }

}

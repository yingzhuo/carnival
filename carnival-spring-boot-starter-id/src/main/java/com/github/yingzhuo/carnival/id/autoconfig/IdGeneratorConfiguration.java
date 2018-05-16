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
import com.github.yingzhuo.carnival.id.impl.LongSnowflakeIdGenerator;
import com.github.yingzhuo.carnival.id.impl.StringSnowflakeIdGenerator;
import com.github.yingzhuo.carnival.id.impl.UUID32IdGenerator;
import com.github.yingzhuo.carnival.id.impl.UUID36IdGenerator;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(IdGeneratorConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.id", name = "enabled", havingValue = "true", matchIfMissing = true)
public class IdGeneratorConfiguration {

    private final Props props;

    public IdGeneratorConfiguration(Props props) {
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
            case SNOWFLAKE_STRING:
                return new StringSnowflakeIdGenerator(props.getSnowflake().getWorkerId(), props.getSnowflake().pad);
            case SNOWFLAKE_LONG:
                return new LongSnowflakeIdGenerator(props.getSnowflake().getWorkerId());
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Data
    @ConfigurationProperties("carnival.id")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.UUID_32;
        private Snowflake snowflake = new Snowflake();

        @Data
        static class Snowflake {
            private long workerId = 0L;
            private int pad = 32;
        }
    }

}

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

import com.github.yingzhuo.carnival.id.IdGenerator;
import com.github.yingzhuo.carnival.id.IdGeneratorAlgorithm;
import com.github.yingzhuo.carnival.id.impl.SnowflakeLongIdGenerator;
import com.github.yingzhuo.carnival.id.impl.SnowflakeStringIdGenerator;
import com.github.yingzhuo.carnival.id.impl.UUIDGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(IdGeneratorAutoConfig1.Props.class)
@ConditionalOnProperty(prefix = "carnival.id-generator", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingClass({"com.github.yingzhuo.snowflake.Snowflake"})
public class IdGeneratorAutoConfig1 {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator<?> idGenerator(Props props) {
        switch (props.getAlgorithm()) {
            case SNOWFLAKE:
            case SNOWFLAKE_STRING:

                long workerId = props.getSnowflake().getWorkerId();
                long dataCenterId = props.getSnowflake().getDataCenterId();

                if (workerId < 1) {
                    workerId = RandomUtils.nextInt(0, 32);
                }

                if (dataCenterId < 1) {
                    dataCenterId = RandomUtils.nextInt(0, 32);
                }

                if (props.getAlgorithm() == IdGeneratorAlgorithm.SNOWFLAKE) {
                    return new SnowflakeLongIdGenerator(
                            workerId,
                            dataCenterId
                    );
                } else {
                    return new SnowflakeStringIdGenerator(
                            workerId,
                            dataCenterId,
                            props.getSnowflake().getLength(),
                            props.getSnowflake().getPadCharacter()
                    );
                }
            case UUID_32:
                return new UUIDGenerator(true);
            case UUID_36:
                return new UUIDGenerator(false);
            case NULL:
                return (IdGenerator<Object>) () -> null;
            default:
                throw new AssertionError(); // 程序不可能运行到此处
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.id-generator")
    static class Props {
        private boolean enabled = true;
        private IdGeneratorAlgorithm algorithm = IdGeneratorAlgorithm.SNOWFLAKE;
        private Snowflake snowflake = new Snowflake();
    }

    @Getter
    @Setter
    static class Snowflake {
        private long workerId = -1L;
        private long dataCenterId = -1L;
        private int length = -1;
        private char padCharacter = '0';
    }

}

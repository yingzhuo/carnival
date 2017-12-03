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
import com.github.yingzhuo.carnival.id.StringIdGenerator;
import com.github.yingzhuo.carnival.id.impl.SnowflakeIdGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@EnableConfigurationProperties({
        IdGeneratorConfiguration.Props.class,
        IdGeneratorConfiguration.SnowflakeProps.class
})
@ConditionalOnProperty(prefix = "carnival.id", name = "enabled", havingValue = "true", matchIfMissing = true)
public class IdGeneratorConfiguration {

    private final Props props;
    private final SnowflakeProps snowflakeProps;

    public IdGeneratorConfiguration(Props props, SnowflakeProps snowflakeProps) {
        this.props = props;
        this.snowflakeProps = snowflakeProps;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public StringIdGenerator stringIdGenerator() {

        switch (props.getAlgorithm()) {
            case UUID:
            case UUID32:
                return () -> UUID.randomUUID().toString().replaceAll("-", "");
            case UUID36:
                return () -> UUID.randomUUID().toString();
            case SNOWFLAKE:
                return new SnowflakeIdGenerator(snowflakeProps.getWorkerId(), snowflakeProps.getPad());
        }

        throw new IllegalStateException();
    }

    @Data
    @ConfigurationProperties("carnival.id")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.UUID32;
    }

    @Data
    @ConfigurationProperties("carnival.id.snowflake")
    static class SnowflakeProps {
        private long workerId = 0L;
        private int pad = 32;
    }

}

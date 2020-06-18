/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.autoconfig;

import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeatedConfigurer;
import com.github.yingzhuo.carnival.restful.norepeated.core.NoRepeatedInterceptor;
import com.github.yingzhuo.carnival.restful.norepeated.factory.NoRepeatedTokenFactory;
import com.github.yingzhuo.carnival.restful.norepeated.factory.NoRepeatedTokenFactoryImpl;
import com.github.yingzhuo.carnival.restful.norepeated.parser.NoRepeatedTokenParser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 应卓
 * @since 1.6.7
 */
@EnableConfigurationProperties(NoRepeatedCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.no-repeated", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
public class NoRepeatedCoreAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private ExceptionTransformer injectedExceptionTransformer;

    @Autowired(required = false)
    private NoRepeatedTokenParser injectedNoRepeatedTokenParser;

    @Autowired(required = false)
    private NoRepeatedConfigurer configurer = new NoRepeatedConfigurer() {
    };

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public NoRepeatedTokenFactory noRepeatedTokenFactory(Props props) {
        return new NoRepeatedTokenFactoryImpl(
                redisConnectionFactory,
                props.getToken().getTimeToLive(),
                props.getRedisKey().getPrefix(),
                props.getRedisKey().getSuffix()
        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final NoRepeatedInterceptor interceptor = new NoRepeatedInterceptor(redisConnectionFactory);
        interceptor.setExceptionTransformer(getExceptionTransformer());
        interceptor.setTokenParser(getNoRepeatedTokenParser());

        registry.addInterceptor(interceptor)
                .addPathPatterns(configurer.getPathPatterns())
                .order(configurer.getOrder());
    }

    private NoRepeatedTokenParser getNoRepeatedTokenParser() {
        if (injectedNoRepeatedTokenParser != null) {
            return injectedNoRepeatedTokenParser;
        }
        return configurer.getNoRepeatedTokenParser();
    }

    private ExceptionTransformer getExceptionTransformer() {
        if (injectedExceptionTransformer != null) {
            return injectedExceptionTransformer;
        }
        return configurer.getExceptionTransformer();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.no-repeated")
    public static class Props {
        private boolean enable = true;
        private Token token = new Token();
        private RedisKey redisKey = new RedisKey();
    }

    @Getter
    @Setter
    static class Token {
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration timeToLive = Duration.ofMinutes(5L);
    }

    @Getter
    @Setter
    static class RedisKey {
        private String prefix = "carnival-no-repeated-token-";
        private String suffix = "";
    }
}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.autoconfig;

import com.github.yingzhuo.carnival.jwt.JwtInfoTransform;
import com.github.yingzhuo.carnival.jwt.JwtTokenGenerator;
import com.github.yingzhuo.carnival.jwt.impl.SimpleJwtTokenGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
@EnableConfigurationProperties({
        JwtConfiguration.JwtProps.class
})
@ConditionalOnProperty(prefix = "carnival.pinyin", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JwtConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenGenerator jwtTokenGenerator(JwtProps props, JwtInfoTransform transform) {
        return new SimpleJwtTokenGenerator(props.getSecret(), transform);
    }

    enum SignatureAlgorithm {
        HMAC256 // 暂时只支持本算法
    }

    @Data
    @ConfigurationProperties("carnival.jwt")
    static class JwtProps implements InitializingBean {
        private boolean enabled = true;
        private String secret = JwtProps.class.getName();
        private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC256;

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.hasText(getSecret(), "'carnival.jwt.secret' should NOT be null or blank.");
        }
    }

}

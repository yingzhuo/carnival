/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.autoconfig;

import com.github.yingzhuo.carnival.password.SaltGenerator;
import com.github.yingzhuo.carnival.password.StringEncryptor;
import com.github.yingzhuo.carnival.password.impl.DeluxStringEncryptor;
import com.github.yingzhuo.carnival.password.impl.NoopStringEncryptor;
import com.github.yingzhuo.carnival.password.impl.SaltGeneratorImpl;
import com.github.yingzhuo.carnival.password.impl.StandardStringEncryptor;
import com.github.yingzhuo.carnival.password.props.PasswordProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.7.1
 */
@ConditionalOnProperty(prefix = "carnival.password", name = "enabled", havingValue = "true", matchIfMissing = true)
public class StringEncryptorAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public SaltGenerator saltGenerator() {
        return new SaltGeneratorImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public StringEncryptor stringEncryptor(PasswordProps props) {
        switch (props.getStringEncryptor().getAlgorithm()) {
            case standard:
                return new StandardStringEncryptor();
            case delux:
                return new DeluxStringEncryptor();
            case noop:
                return new NoopStringEncryptor();
            default:
                throw new AssertionError();
        }
    }

}

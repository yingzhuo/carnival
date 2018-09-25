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

import com.github.yingzhuo.carnival.password.Algorithm;
import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import com.github.yingzhuo.carnival.password.impl.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(PasswordEncrypterAutoConfig.Props.class)
public class PasswordEncrypterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncrypter passwordEncrypter(Props props) {
        switch (props.getAlgorithm()) {
            case MD5:
                return new MD5PasswordEncrypter(props.getMd5Salt());
            case BCRYPT:
                return new BCryptPasswordEncrypter();
            case MD2:
                return new MD2PasswordEncrypter(props.getMd2Salt());
            case SHA1:
                return new SHA1PasswordEncrypter(props.getSha1Salt());
            case SHA256:
                return new SHA256PasswordEncrypter(props.getSha256Salt());
            case BASE64:
                return new Base64PasswordEncrypter();
            default:
                throw new IllegalStateException();
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.password")
    static class Props {
        private Algorithm algorithm = Algorithm.MD5;
        private String md5Salt = "";
        private String md2Salt = "";
        private String sha1Salt = "";
        private String sha256Salt = "";
    }

}

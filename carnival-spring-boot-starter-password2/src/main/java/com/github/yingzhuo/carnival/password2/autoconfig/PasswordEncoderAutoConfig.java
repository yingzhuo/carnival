/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password2.autoconfig;

import com.github.yingzhuo.carnival.password2.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
@SuppressWarnings("deprecation")
@ConditionalOnProperty(prefix = "carnival.password2", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(PasswordEncoderAutoConfig.Props.class)
public class PasswordEncoderAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder2(Props props) {
        if (props.isDelegatingMode()) {
            return createDelegatingPasswordEncoder(props.getAlgorithm().getId());
        } else {
            return props.getAlgorithm().getPasswordEncoder();
        }
    }

    private PasswordEncoder createDelegatingPasswordEncoder(String defaultId) {
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        for (Algorithm alg : Algorithm.values()) {
            encoders.put(alg.getId(), alg.getPasswordEncoder());
        }
        return new DelegatingPasswordEncoder(defaultId, encoders);
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.password2")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.MD5;
        private boolean delegatingMode = true;
    }

//    public static void main(String[] args) {
//        val props = new Props();
//        props.algorithm = Algorithm.PBKDF2;
//        props.delegatingMode = true;
//
//        val pe = new PasswordEncoderAutoConfig().passwordEncoder2(props);
//        val result = pe.encode("133810");
//        System.out.println(result);
//
//        System.out.println(pe.matches("133810", "{noop}133810"));
//        System.out.println(pe.matches("133810", "{reverse}018331"));
//        System.out.println(pe.matches("133810", "{pbkdf2}9092ad74b3dbbfc4d35cdb73e646fb3aa02765efbbe7ba1173e38cbfac0eb3e1343dd58433341b09"));
//        System.out.println(pe.matches("133810", "{ldap}{SSHA}FLi3Q3YX3dIwomvK8fxeI+VEIbz+foW6gxAO2g=="));
//        System.out.println(pe.matches("133810", "{SHA-256}{yHuETCIWGF/LpFS13lMoRhvUCu520Dgf9LFbSZomtGI=}3b4f9b8c9adcbbffc9157cf86bd7c98224d718b2c622e9462a3c4804b1ca0eda"));
//        System.out.println(pe.matches("133810", "{SHA-1}{kcW/KV7IGyoEu4vJYADLS5b36Z3EP4CGLhNSoiMYcvg=}8a26c030cb3989ce88177c2051ad447ff804d555"));
//        System.out.println(pe.matches("133810", "{bcrypt}$2a$10$fQ9TNZ3BfsR/VMojuNGVR.fEunMIlfyXX.tgKSRnHTOth4UoEB11a"));
//        System.out.println(pe.matches("133810", "{MD5}{deClgkPh5X41sek8Od7KljrDMAHiVCqaYfGLoKmKQtY=}6318b497c26cd01e776002e6f44b7f51"));
//        System.out.println(pe.matches("133810", "{MD4}{vrPu2lsznH6IYzeNSJsdDW2bRxtAMl61iKPKLCSWjz0=}67765cceeaa9c89bf2116ee2cc29c94d"));
//        System.out.println(pe.matches("133810", "{scrypt}$e0801$qrazwLpSQIIq7B4B78ffqXD3wp2VAcUu0FSHiFZR9RBgfC49jJqyMP2E1sAWzkJu45LBpJZts5Vo3xRDS7qJAw==$uZk1wyP1rvioU4iRzLnwhG6GYD7J9Bpc9GnVkeeYFE8="));
//    }

}

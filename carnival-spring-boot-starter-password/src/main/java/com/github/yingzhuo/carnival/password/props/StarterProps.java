/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.props;

import com.github.yingzhuo.carnival.password.algorithm.PasswordEncoderAlgorithm;
import com.github.yingzhuo.carnival.password.algorithm.StringEncryptorAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 * @since 1.7.1
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.password")
public class StarterProps {

    private boolean enabled = true;
    private PasswordEncoder passwordEncoder = new PasswordEncoder();
    private StringEncryptor stringEncryptor = new StringEncryptor();

    @Getter
    @Setter
    public static class PasswordEncoder {
        private PasswordEncoderAlgorithm encoding = PasswordEncoderAlgorithm.md5;
        private PasswordEncoderAlgorithm unmapped = PasswordEncoderAlgorithm.md5;
    }

    @Getter
    @Setter
    public static class StringEncryptor {
        private StringEncryptorAlgorithm algorithm = StringEncryptorAlgorithm.delux;
    }

}

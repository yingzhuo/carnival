/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret;

import lombok.*;

import java.io.Serializable;

/**
 * @author 应卓
 */
public interface WebSecretContext extends Serializable {

    public String getNonce();

    public String getTimestamp();

    public String getClientId();

    public String getSignature();

    public String getSecret();


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Impl implements WebSecretContext {
        private String nonce;
        private String timestamp;
        private String clientId;
        private String signature;
        private String secret;
    }

}

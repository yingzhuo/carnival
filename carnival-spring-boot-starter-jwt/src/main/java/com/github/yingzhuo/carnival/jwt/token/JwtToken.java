/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.token;

import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import lombok.val;

import java.io.Serializable;

/**
 * @author 应卓
 */
public class JwtToken extends StringToken implements Serializable {

    private static final long serialVersionUID = 7058971979786348534L;

    private final String header;
    private final String payload;
    private final String signature;

    public JwtToken(String value) {
        super(value);
        val parts = value.split("\\.");
        this.header = parts[0];
        this.payload = parts[1];
        this.signature = parts[2];
    }

    public final String getHeader() {
        return header;
    }

    public final String getPayload() {
        return payload;
    }

    public final String getSignature() {
        return signature;
    }

}

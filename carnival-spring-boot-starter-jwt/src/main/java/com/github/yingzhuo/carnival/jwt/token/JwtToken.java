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

import java.util.Objects;

/**
 * @author 应卓
 */
public class JwtToken extends StringToken {

    private static final long serialVersionUID = 7058971979786348534L;

    private final String header;        // 头部
    private final String body;          // 身体
    private final String signature;     // 签名

    public JwtToken(String value) {
        super(value);

        final String[] parts = Objects.requireNonNull(value).split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Not a jwt token.");
        }

        this.header = parts[0];
        this.body = parts[1];
        this.signature = parts[2];
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return super.getValue();
    }

}

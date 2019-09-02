/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.token;

import com.github.yingzhuo.carnival.restful.security.jwt.exception.IllegalTokenPatternException;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import lombok.val;

/**
 * @author 应卓
 * @since 1.1.6
 */
public class JwtToken extends StringToken {

    private final String headerPart;
    private final String bodyPart;
    private final String signaturePart;

    public static JwtToken of(String value) {
        return new JwtToken(value);
    }

    public JwtToken(String value) {
        super(value);

        val parts = value.split(".");

        if (parts.length != 3) {
            throw new IllegalTokenPatternException();
        }

        headerPart = parts[0];
        bodyPart = parts[1];
        signaturePart = parts[2];
    }

    public String getHeaderPart() {
        return headerPart;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getSignaturePart() {
        return signaturePart;
    }

}

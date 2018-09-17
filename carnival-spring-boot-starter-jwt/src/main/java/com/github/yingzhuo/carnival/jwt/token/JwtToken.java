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

import java.io.Serializable;

/**
 * @author 应卓
 */
public class JwtToken extends StringToken implements Serializable {

    private static final long serialVersionUID = 7058971979786348534L;

    public JwtToken(String value) {
        super(value);
    }

}

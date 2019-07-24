/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.token;

import java.io.Serializable;

/**
 * 用户令牌
 *
 * @author 应卓
 * @see UsernamePasswordToken
 * @see StringToken
 */
@FunctionalInterface
public interface Token extends Serializable {

    public String asString();

    @Override
    public boolean equals(Object other);

    @Override
    public int hashCode();

}

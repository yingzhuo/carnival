/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import java.io.Serializable;

/**
 * 用户令牌
 *
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.impl.UsernamePasswordToken
 * @see com.github.yingzhuo.carnival.restful.security.impl.StringToken
 */
public interface Token extends Serializable {
}

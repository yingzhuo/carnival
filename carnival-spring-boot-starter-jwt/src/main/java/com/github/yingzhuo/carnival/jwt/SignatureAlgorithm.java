/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt;

/**
 * @author 应卓
 */
public enum SignatureAlgorithm {

    // 其他算法暂不支持

    HMAC256,

    HMAC384,

    HMAC512,

    RSA256,

    RSA384,

    RSA512

}

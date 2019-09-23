/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock;

/**
 * @author 应卓
 * @since 1.1.10
 */
interface Constant {
    static final String LOCK_SUCCESS = "OK";
    static final String SET_IF_NOT_EXIST = "NX";
    static final String SET_WITH_EXPIRE_TIME = "PX";
    static final Long RELEASE_SUCCESS = 1L;
}

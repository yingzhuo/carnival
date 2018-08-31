/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.exception;

/**
 * @author 应卓
 */
public class CannotGetLockException extends RuntimeException {

    private static final long serialVersionUID = 1064277687256783689L;

    private String key;

    public CannotGetLockException() {
        this(null);
    }

    public CannotGetLockException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}

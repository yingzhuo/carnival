/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.lock.exception;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NotAbleToLockException extends RuntimeException {

    private final String springId;
    private final Long threadId;

    public NotAbleToLockException(String springId, Long threadId) {
        this.springId = springId;
        this.threadId = threadId;
    }

    public NotAbleToLockException(String message, String springId, Long threadId) {
        super(message);
        this.springId = springId;
        this.threadId = threadId;
    }

    public String getSpringId() {
        return springId;
    }

    public Long getThreadId() {
        return threadId;
    }

}

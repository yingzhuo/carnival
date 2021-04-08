/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.failure;

/**
 * @author 应卓
 * @since 1.8.1
 */
public class StartupFailure extends RuntimeException {

    private final String description;
    private final String action;

    public StartupFailure(String description, String action) {
        this.description = description;
        this.action = action;
    }

    public StartupFailure(Throwable cause, String description, String action) {
        super(cause);
        this.description = description;
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public String getAction() {
        return action;
    }

}

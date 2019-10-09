/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json;

/**
 * @author 应卓
 * @see com.fasterxml.jackson.annotation.JsonView
 */
public interface Views {

    /**
     * 普通
     *
     * @since 1.1.12
     */
    public static interface Normal {
    }

    /**
     * 敏感信息
     *
     * @since 1.1.12
     */
    public static interface Secret extends Normal {
    }

    /**
     * 危险信息
     *
     * @since 1.2.0
     */
    public static interface Danger extends Secret {
    }

}

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
 * @since 1.1.12
 */
public interface Views {

    /**
     * 普通
     */
    public static interface Normal {
    }

    /**
     * 敏感信息
     */
    public static interface Secret extends Normal {
    }

}

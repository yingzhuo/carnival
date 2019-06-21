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

import com.github.yingzhuo.carnival.common.StringCoded;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.0.4
 */
public interface ApiResult<T> extends Serializable, StringCoded {

    public String getErrorMessage();

    public T getPayload();

    public boolean isEmpty();

    public int size();

}
/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module.jackson;

import org.springframework.data.domain.Page;

import java.util.Collections;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class PageUtils {

    private PageUtils() {
    }

    public static <T> Page<T> empty() {
        return empty(0, 1);
    }

    public static <T> Page<T> empty(int pageNumber, int pageSize) {
        return new PageMixIn.SimplePageImpl(Collections.emptyList(), pageNumber, pageSize, 0L);
    }

}

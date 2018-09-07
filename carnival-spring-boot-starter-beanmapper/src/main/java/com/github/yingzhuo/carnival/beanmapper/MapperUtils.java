/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.beanmapper;

import com.baidu.unbiz.easymapper.MapperFactory;

import java.util.function.Supplier;

/**
 * @author 应卓
 */
public final class MapperUtils {

    private MapperUtils() {
        super();
    }

    public static <A, B> B map(A a, B b) {
        return MapperFactory.getCopyByRefMapper().map(a, b);
    }

    public static <A, B> B map(A a, Supplier<B> supplier) {
        return map(a, supplier.get());
    }

}

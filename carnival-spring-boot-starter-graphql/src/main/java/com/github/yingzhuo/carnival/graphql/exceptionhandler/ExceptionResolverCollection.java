/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.exceptionhandler;

import java.util.Iterator;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
@FunctionalInterface
public interface ExceptionResolverCollection extends Iterable<ExceptionResolver> {

    public List<ExceptionResolver> getExceptionResolvers();

    @Override
    default Iterator<ExceptionResolver> iterator() {
        return getExceptionResolvers().listIterator();
    }

}

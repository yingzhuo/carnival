/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.target;

/**
 * @author 应卓
 * @since 1.6.17
 */
public final class BrokenUrlSupplier implements UrlSupplier {

    private BrokenUrlSupplier() {
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException();
    }

}

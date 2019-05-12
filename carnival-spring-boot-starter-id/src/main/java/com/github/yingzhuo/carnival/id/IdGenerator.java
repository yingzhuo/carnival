/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id;

import java.util.function.Supplier;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface IdGenerator<ID> extends Supplier<ID> {

    public ID nextId();

    @Override
    default ID get() {
        return nextId();
    }

}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface IdGenerator<ID> {

    public ID nextId();

    public default List<ID> nextId(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        List<ID> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(nextId());
        }
        return Collections.unmodifiableList(list);
    }

}

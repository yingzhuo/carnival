/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.selector;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @param <T> node type
 * @author 应卓
 * @since 1.3.1
 */
public abstract class AbstractRandomNodeSelector<T> implements NodeSelector<T> {

    private final Random random = new Random();

    @Override
    public T select(Set<T> set) {

        if (set == null || set.isEmpty()) {
            return null;
        }

        int size = set.size();

        if (size == 1) {
            return set.iterator().next();
        }

        List<T> list = set.parallelStream().collect(Collectors.toList());
        return list.get(random.nextInt(size));
    }

}

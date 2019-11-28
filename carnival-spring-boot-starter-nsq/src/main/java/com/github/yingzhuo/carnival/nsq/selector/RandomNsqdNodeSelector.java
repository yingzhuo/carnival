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

import com.github.yingzhuo.carnival.nsq.NsqdNodeSelector;
import com.github.yingzhuo.carnival.nsq.node.NsqdNode;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class RandomNsqdNodeSelector implements NsqdNodeSelector {

    private final Random random = new Random();

    @Override
    public NsqdNode select(Set<NsqdNode> set) {

        int size = set.size();

        if (size == 1) {
            return set.iterator().next();
        }

        List<NsqdNode> list = set.parallelStream().collect(Collectors.toList());
        return list.get(random.nextInt(size));
    }

}

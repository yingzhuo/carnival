/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.responsibility;

import java.util.Iterator;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.14
 */
public final class ResponsibilityChain implements Iterable<Responsibility> {

    private final List<Responsibility> responsibilities;

    private ResponsibilityChain(List<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public static ResponsibilityChain of(List<Responsibility> responsibilities) {
        return new ResponsibilityChain(responsibilities);
    }

    @Override
    public Iterator<Responsibility> iterator() {
        return responsibilities.iterator();
    }

    public int size() {
        return responsibilities.size();
    }

    public boolean isEmpty() {
        return responsibilities.isEmpty();
    }

}

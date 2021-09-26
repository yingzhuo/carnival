/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.support;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.25
 */
public final class PredicationSet implements Serializable {

    private final List<Predicate> list = new ArrayList<>();

    private PredicationSet() {
    }

    public static PredicationSet newInstance() {
        return new PredicationSet();
    }

    public PredicationSet add(Predicate predicate, Predicate... more) {
        list.add(predicate);
        if (more != null && more.length != 0) {
            list.addAll(Arrays.asList(more));
        }
        return this;
    }

    public PredicationSet clear() {
        list.clear();
        return this;
    }

    public Predicate[] toArray() {
        return list.toArray(new Predicate[0]);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

}

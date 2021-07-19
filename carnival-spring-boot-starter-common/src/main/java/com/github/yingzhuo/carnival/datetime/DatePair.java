/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datetime;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.*;

/**
 * DatePair表达的是一个左右闭区间
 *
 * @author 应卓
 * @since 1.9.10
 */
public final class DatePair implements Iterable<Date>, Serializable {

    private final Set<Date> set;
    private Date l;
    private Date r;
    private List<Date> list = new ArrayList<>();

    public DatePair(Date l, Date r) {
        Objects.requireNonNull(l);
        Objects.requireNonNull(r);
        this.l = DateUtils.truncate(l, Calendar.DATE);
        this.r = DateUtils.truncate(r, Calendar.DATE);

        if (r.before(r)) {
            Date tmp = this.l;
            this.l = this.r;
            this.r = tmp;
        }

        for (Date it = l; !DateUtils.isSameDay(it, DateUtils.addDays(r, 1)); it = DateUtils.addDays(it, 1)) {
            list.add(it);
        }

        list = Collections.unmodifiableList(list);
        set = Collections.unmodifiableSet(new HashSet<>(list));
    }

    public Date left() {
        return l;
    }

    public Date right() {
        return r;
    }

    public List<Date> asList() {
        return list;
    }

    public Set<Date> asSet() {
        return set;
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Date> iterator() {
        return list.iterator();
    }

}

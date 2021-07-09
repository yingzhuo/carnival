package com.github.yingzhuo.carnival.datetime;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.*;

/**
 * DatePair表达的是一个左右闭区间
 *
 * @author 应卓
 */
public final class DatePair implements Iterable<Date>, Serializable {

    private Date l;
    private Date r;
    private List<Date> list = new ArrayList<>();
    private final Set<Date> set;

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

    @Override
    public Iterator<Date> iterator() {
        return list.iterator();
    }

}

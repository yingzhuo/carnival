/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.time;

import lombok.val;
import lombok.var;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * @author 应卓
 */
@Deprecated
public class DateRange implements Serializable, Iterable<Date> {

    public static DateRange of(Date start, Date end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        return new DateRange(start, end);
    }

    public static DateRange of(String pattern, String start, String end) {
        try {
            return of(DateUtils.parseDate(start, pattern), DateUtils.parseDate(end, pattern));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private final Date d1;
    private final Date d2;

    private DateRange(Date d1, Date d2) {
        var t1 = DateUtils.truncate(d1, Calendar.DATE);
        var t2 = DateUtils.truncate(d2, Calendar.DATE);

        if (t1.after(t2)) {
            this.d1 = t2;
            this.d2 = t1;
        } else {
            this.d1 = t1;
            this.d2 = t2;
        }
    }

    @Override
    public Iterator<Date> iterator() {
        if (d2.before(d1) || DateUtils.isSameDay(d1, d2)) {
            return EmptyDateRangeIterator.INSTANCE;
        }

        return new DefaultDateRange(d1, d2);
    }

    public Date getLeftClose() {
        return this.d1;
    }

    public Date getRightOpen() {
        return this.d2;
    }

    @Override
    public String toString() {
        return toString("yyyy-MM-dd");
    }

    public String toString(String pattern) {
        return String.format("DateRange('%s' to '%s')", DateFormatUtils.format(d1, pattern), DateFormatUtils.format(d2, pattern));
    }

    public List<DateRange> splitByWeek() {
        final List<DateRange> list = new ArrayList<>();

        Date s = d1;
        Date e = s;

        val f = getDayOfWeek(s);
        if (f != 1) {
            var plus = 8 - f;
            e = DateUtils.addDays(s, plus);
            list.add(of(s, e));
        }

        while (true) {
            s = e;
            e = DateUtils.addDays(s, 7);

            if (!contains(s)) {
                break;
            }

            if (!contains(e)) {
                list.add(of(s, d2));
                break;
            } else {
                list.add(of(s, e));
            }
        }

        return list;
    }

    public List<DateRange> splitByMonth() {
        final List<DateRange> list = new ArrayList<>();

        Date s = d1;
        Date e = DateUtils.truncate(DateUtils.addMonths(s, 1), Calendar.MONTH);

        if (!contains(e)) {
            list.add(of(s, d2));
            return list;
        }

        while (true) {
            if (!contains(s)) {
                break;
            }

            if (!contains(e)) {
                list.add(of(s, d2));
                break;
            }

            if (contains(s) && contains(e)) {
                list.add(of(s, e));
                s = DateUtils.truncate(DateUtils.addMonths(s, 1), Calendar.MONTH);
                e = DateUtils.truncate(DateUtils.addMonths(e, 1), Calendar.MONTH);
            }
        }

        return list;
    }

    public List<DateRange> splitByYear() {
        final List<DateRange> list = new ArrayList<>();

        Date s = d1;
        Date e = DateUtils.truncate(DateUtils.addYears(s, 1), Calendar.YEAR);

        if (!contains(e)) {
            list.add(of(s, d2));
            return list;
        }

        while (true) {
            if (!contains(s)) {
                break;
            }

            if (!contains(e)) {
                list.add(of(s, d2));
                break;
            }

            if (contains(s) && contains(e)) {
                list.add(of(s, e));
                s = DateUtils.truncate(DateUtils.addYears(s, 1), Calendar.YEAR);
                e = DateUtils.truncate(DateUtils.addYears(e, 1), Calendar.YEAR);
            }
        }

        return list;
    }

    private int getWeekOfYear(Date date) {
        val cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    private int getDayOfWeek(Date date) {
        val cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public boolean contains(Date date) {
        date = DateUtils.truncate(date, Calendar.DATE);
        return (DateUtils.isSameDay(date, d1) || date.after(d1)) && date.before(d2);
    }

    // ---------------------------------------------------------------------------------------------------------------

    private static class EmptyDateRangeIterator implements Iterator<Date> {

        private static final EmptyDateRangeIterator INSTANCE = new EmptyDateRangeIterator();

        private EmptyDateRangeIterator() {
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Date next() {
            throw new NoSuchElementException();
        }
    }

    private static class DefaultDateRange implements Iterator<Date> {

        private Date current;
        private final Date t1;
        private final Date t2;

        public DefaultDateRange(Date t1, Date t2) {
            this.current = t1;
            this.t1 = t1;
            this.t2 = t2;
        }

        @Override
        public boolean hasNext() {
            return current.before(t2);
        }

        @Override
        public Date next() {
            if (hasNext()) {
                val t = current;
                current = DateUtils.addDays(current, 1);
                return t;
            }
            throw new NoSuchElementException();
        }
    }

}

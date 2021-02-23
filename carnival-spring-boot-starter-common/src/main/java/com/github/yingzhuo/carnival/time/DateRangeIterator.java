/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.time;

import java.time.Duration;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 应卓
 * @since 1.6.25
 */
class DateRangeIterator implements Iterator<Date> {

    private final long end;
    private final long step;
    private long current;

    DateRangeIterator(Date begin, Date end, Duration step) {
        this.current = begin.getTime();
        this.end = end.getTime();
        this.step = step.toMillis();
    }

    @Override
    public boolean hasNext() {
        return current < end;
    }

    @Override
    public Date next() {
        final long l = current;
        this.current += step;
        return new Date(l);
    }
}

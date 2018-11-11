/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public class AroundCurrentTimeMatchValidator implements ConstraintValidator<AroundCurrentTime, Date> {

    private int past;
    private int future;
    private int truncation;
    private TimeUnit timeUnit;

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) return true;

        final long date = value.getTime();
        long now;

        if (truncation == -1) {
            now = System.currentTimeMillis();
        } else {
            now = DateUtils.truncate(new Date(), truncation).getTime();
        }

        return checkInFuture(date, now) && checkInPast(date, now);
    }

    private boolean checkInFuture(long date, long now) {
        if (future < 0) {
            return true;
        }
        long end = now + timeUnit.toMillis(future);
        return date <= end;
    }

    private boolean checkInPast(long date, long now) {
        if (past < 0) {
            return true;
        }
        long begin = now - timeUnit.toMillis(past);
        return date >= begin;
    }

    @Override
    public void initialize(AroundCurrentTime annotation) {
        this.past = annotation.past();
        this.future = annotation.future();
        this.truncation = annotation.dateTruncation().getValue();
        this.timeUnit = annotation.timeUnit();
    }

}

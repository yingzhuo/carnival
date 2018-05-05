/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.impl;

import com.github.yingzhuo.carnival.id.LongIdGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongSnowflakeIdGenerator implements LongIdGenerator {

    private final static long TWEPOCH = 1361753741828L;
    private final static long WORKER_ID_BITS = 4L;
    private final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;
    private final static long SEQUENCE_BITS = 10L;
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;
    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public LongSnowflakeIdGenerator(long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            final String msg = String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID);
            log.warn(msg);
            log.warn("worker Id set to 0.");
            workerId = 0L;
        }
        this.workerId = workerId;
    }

    @Override
    public synchronized Long nextId() {
        long timestamp = timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            final String msg = String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp);
            throw new RuntimeException(msg);
        }

        this.lastTimestamp = timestamp;
        return ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT)) | (this.workerId << WORKER_ID_SHIFT) | (this.sequence);
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public long getWorkerId() {
        return workerId;
    }

}

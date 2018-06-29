/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel.config;

import com.github.yingzhuo.carnival.sentinel.Sentinel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public class SentinelConfig implements Map.Entry<String, SentinelConfig.SimpleSentinel> {

    public static SentinelConfig.Builder builder() {
        return new SentinelConfig.Builder();
    }

    private SentinelConfig() {
        super();
    }

    private String pattern;
    private SimpleSentinel sentinel;

    @Override
    public String getKey() {
        return this.pattern;
    }

    @Override
    public SimpleSentinel getValue() {
        return this.sentinel;
    }

    @Override
    public SimpleSentinel setValue(SimpleSentinel value) {
        SimpleSentinel old = getValue();
        this.sentinel = value;
        return old;
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static final class SimpleSentinel implements Sentinel {

        private static final long serialVersionUID = 1161838190918159737L;

        protected long minAccessIntervalInMillis = -1;
        protected long maxAccessCount = -1;
        protected TimeUnit maxAccessCountTimeUnit = TimeUnit.DAYS;
        protected int maxAccessCountTimeUnitNumber = 1;

        @Override
        public long getMinAccessIntervalInMillis() {
            return this.minAccessIntervalInMillis;
        }

        @Override
        public long getMaxAccessCount() {
            return this.maxAccessCount;
        }

        @Override
        public TimeUnit getMaxAccessCountTimeUnit() {
            return this.maxAccessCountTimeUnit;
        }

        @Override
        public int getMaxAccessCountTimeUnitNumber() {
            return this.maxAccessCountTimeUnitNumber;
        }

    }

    // ----------------------------------------------------------------------------------------------------------------

    public static final class Builder {

        private String pattern;
        private SimpleSentinel sentinel = new SimpleSentinel();

        public Builder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder minAccessIntervalInMillis(long minAccessIntervalInMillis) {
            this.sentinel.minAccessIntervalInMillis = minAccessIntervalInMillis;
            return this;
        }

        public Builder maxAccessCount(long maxAccessCount) {
            this.sentinel.maxAccessCount = maxAccessCount;
            return this;
        }

        public Builder maxAccessCountTimeUnit(TimeUnit maxAccessCountTimeUnit) {
            this.sentinel.maxAccessCountTimeUnit = maxAccessCountTimeUnit;
            return this;
        }

        public Builder maxAccessCountTimeUnitNumber(int maxAccessCountTimeUnitNumber) {
            this.sentinel.maxAccessCountTimeUnitNumber = maxAccessCountTimeUnitNumber;
            return this;
        }

        public SentinelConfig build() {
            Objects.requireNonNull(pattern);
            SentinelConfig config = new SentinelConfig();
            config.pattern = this.pattern;
            config.sentinel = this.sentinel;
            return config;
        }
    }

}

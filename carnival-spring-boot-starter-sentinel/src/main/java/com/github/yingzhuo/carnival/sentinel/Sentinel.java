/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public interface Sentinel extends Serializable {

    public long getMinAccessIntervalInMillis();

    public long getMaxAccessCount();

    public TimeUnit getMaxAccessCountTimeUnit();

    public int getMaxAccessCountTimeUnitNumber();

}

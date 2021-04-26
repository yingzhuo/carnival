/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.util;

import com.github.yingzhuo.carnival.id.IdGenerator;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.util.List;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class IdGeneratorUtils {

    private IdGeneratorUtils() {
    }

    public static <T> T nextId() {
        return (T) ((IdGenerator<?>) SpringUtils.getBean(IdGenerator.class)).nextId();
    }

    public static <T> List<T> nextIds(int n) {
        return (List<T>) ((IdGenerator<?>) SpringUtils.getBean(IdGenerator.class)).nextId(n);
    }

}

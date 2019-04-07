/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.tool.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.github.yingzhuo.carnival.tool.KeyMapper;

/**
 * @author 应卓
 */
public final class KeyMapperUtils {

    private KeyMapperUtils() {
        super();
    }

    public static String map(CharSequence key) {
        return SpringUtils.getBean(KeyMapper.class).map(key);
    }

}

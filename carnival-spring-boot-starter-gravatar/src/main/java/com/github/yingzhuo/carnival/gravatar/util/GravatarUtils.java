/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.gravatar.util;

import com.github.yingzhuo.carnival.gravatar.GravatarFactory;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 */
public final class GravatarUtils {

    private GravatarUtils() {
    }

    public static String create(String email) {
        return SpringUtils.getBean(GravatarFactory.class).create(email);
    }

    public static String create(String email, int size) {
        return SpringUtils.getBean(GravatarFactory.class).create(email, size);
    }
}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.3.3
 */
public final class ResourceUtils {

    private ResourceUtils() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Resource loadResource(String location) {
        return SpringUtils.getResourcePatternResolver().getResource(location);
    }

    public static Resource[] loadResources(String locationPattern) {
        try {
            return SpringUtils.getResourcePatternResolver().getResources(locationPattern);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

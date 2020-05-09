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

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class ResourceLoaderUtils {

    private static final ResourcePatternResolver DEFAULT = new PathMatchingResourcePatternResolver();

    private ResourceLoaderUtils() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static ResourceLoader getResourceLoader() {
        return DEFAULT;
    }

    public static ResourcePatternResolver getResourcePatternResolver() {
        return DEFAULT;
    }

}

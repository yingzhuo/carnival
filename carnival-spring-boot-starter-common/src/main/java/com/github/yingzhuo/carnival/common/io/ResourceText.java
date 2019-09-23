/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.1.10
 */
public final class ResourceText implements Serializable {

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    public static String apply(String location) {
        return apply(location, StandardCharsets.UTF_8);
    }

    public static String apply(String location, Charset charset) {
        try {
            val resource = RESOURCE_LOADER.getResource(location);
            val lines = IOUtils.readLines(resource.getInputStream(), charset);
            val result = StringUtils.join(lines, "");
            resource.getInputStream().close();
            return result;
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ResourceText() {
    }

}

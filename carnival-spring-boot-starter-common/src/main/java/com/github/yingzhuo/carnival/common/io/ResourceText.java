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

import org.springframework.core.io.Resource;

import java.io.Serializable;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.3.3
 */
public interface ResourceText extends Serializable {

    public static ResourceText of(String location) {
        return new ResourceTextImpl(location, UTF_8);
    }

    public static ResourceText of(String location, Charset charset) {
        return new ResourceTextImpl(location, charset);
    }

    public static ResourceText of(Resource resource) {
        return new ResourceTextImpl(resource, UTF_8);
    }

    public static ResourceText of(Resource resource, Charset charset) {
        return new ResourceTextImpl(resource, charset);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public String getText();

    public int getLength();

    public default String getTextAsOneLine() {
        return getText().replaceAll("\\n", "");
    }

    // since 1.6.2
    public default String getTextAsOneLineAndTrim() {
        return getTextAsOneLine().trim();
    }

    // since 1.6.2
    public default String getTextAndStripWriteSpaces() {
        return getText().replaceAll("\\s", "");
    }

}

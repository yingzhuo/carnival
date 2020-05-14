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

import com.github.yingzhuo.carnival.spring.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.3.3
 */
public interface ResourceText extends Serializable {

    public static ResourceText of(String location) {
        return new SimpleResourceText(location, StandardCharsets.UTF_8);
    }

    public static ResourceText of(String location, Charset charset) {
        return new SimpleResourceText(location, charset);
    }

    public static ResourceText of(Resource resource) {
        return new SimpleResourceText(resource, StandardCharsets.UTF_8);
    }

    public static ResourceText of(Resource resource, Charset charset) {
        return new SimpleResourceText(resource, charset);
    }

    public String getText();

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

    // -----------------------------------------------------------------------------------------------------------------

    static class SimpleResourceText implements ResourceText {

        private final String text;

        public SimpleResourceText(String location, Charset charset) {
            this(ResourceUtils.loadResource(location), charset);
        }

        public SimpleResourceText(Resource resource, Charset charset) {
            try {
                if (!resource.exists() || !resource.isReadable()) {
                    throw new IOException("Cannot open resource.");
                }
                this.text = IOUtils.toString(resource.getInputStream(), charset);
                resource.getInputStream().close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public String getText() {
            return this.text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

}

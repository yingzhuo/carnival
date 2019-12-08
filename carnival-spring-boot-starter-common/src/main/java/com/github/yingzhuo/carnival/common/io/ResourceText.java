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

import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
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

    public String getText();

    public default String getTextAsOneLine() {
        return Joiner.on("")
                .skipNulls()
                .join(getText().split("\n"));
    }

    public static class SimpleResourceText implements ResourceText {

        private static final ResourceLoader LOADER = new DefaultResourceLoader();

        private final String text;

        public SimpleResourceText(String location, Charset charset) {
            try {
                Resource resource = LOADER.getResource(location);

                if (!resource.exists() || !resource.isReadable()) {
                    throw new IOException();
                }

                Reader reader = new InputStreamReader(resource.getInputStream(), charset);

                this.text = CharStreams.toString(new InputStreamReader(resource.getInputStream(), charset));

                reader.close();
                resource.getInputStream().close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public String getText() {
            return this.text;
        }
    }

}

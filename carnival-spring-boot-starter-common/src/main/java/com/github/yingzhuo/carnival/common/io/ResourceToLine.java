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
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.0.6
 */
public final class ResourceToLine implements Serializable {

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    public static String apply(String location) {
        return apply(location, StandardCharsets.UTF_8);
    }

    public static String apply(String location, Charset charset) {
        return new ResourceToLine(location, charset).getContent();
    }

    private final String location;
    private final String content;
    private final Charset charset;

    public ResourceToLine(String location) {
        this(location, StandardCharsets.UTF_8);
    }

    public ResourceToLine(String location, Charset charset) {
        this.location = location;
        this.charset = charset;
        this.content = init(this.location);
    }

    private String init(String location) {
        try {
            val resource = RESOURCE_LOADER.getResource(location);
            val lines = IOUtils.readLines(resource.getInputStream(), charset);
            val result = StringUtils.join(lines, "");
            resource.getInputStream().close();
            return result;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String toString() {
        return this.content;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContent() {
        return this.content;
    }

    public Charset getCharset() {
        return this.charset;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.schema;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.10.14
 */
public final class SchemaTextBuilder {

    private final StringBuilder buffer = new StringBuilder();

    SchemaTextBuilder() {
    }

    public SchemaTextBuilder append(String text) {
        this.buffer.append(text);
        return this;
    }

    public SchemaTextBuilder append(Resource resource) {
        return append(resource, StandardCharsets.UTF_8);
    }

    public SchemaTextBuilder append(Resource resource, Charset charset) {
        try {
            this.buffer.append(IOUtils.toString(resource.getInputStream(), charset));
            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public SchemaText build() {
        return buffer::toString;
    }

}

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
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

/**
 * @author 应卓
 * @since 1.6.33
 */
class ResourceTextImpl implements ResourceText {

    private final String text;
    private final int length;

    public ResourceTextImpl(String location, Charset charset) {
        this(ResourceUtils.loadResource(location), charset);
    }

    public ResourceTextImpl(Resource resource, Charset charset) {
        try {
            if (!resource.exists() || !resource.isReadable()) {
                throw new IOException("Cannot open resource.");
            }
            this.text = IOUtils.toString(resource.getInputStream(), charset);
            this.length = this.text.length();
            resource.getInputStream().close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int getLength() {
        return this.length;
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
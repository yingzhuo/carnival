/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.yingzhuo.carnival.mustache.MustacheCompiler;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

/**
 * @author 应卓
 * @since 1.1.8
 */
public class DefaultMustacheCompiler implements MustacheCompiler {

    private final MustacheFactory mf;
    private final ResourceLoader resourceLoader;
    private String charset = "UTF-8";

    public DefaultMustacheCompiler() {
        this.mf = new DefaultMustacheFactory();
        this.resourceLoader = new DefaultResourceLoader();
    }

    @Override
    public String compile(String templateResourceLocation, String templateName, Object data) {
        Resource resource = resourceLoader.getResource(templateResourceLocation);
        InputStream inputStream = null;
        Reader reader = null;
        StringWriter sw = null;

        try {
            inputStream = resource.getInputStream();
            reader = new InputStreamReader(resource.getInputStream(), this.charset);
            Mustache mustache = mf.compile(reader, templateName);

            sw = new StringWriter();
            mustache.execute(sw, data);
            sw.flush();
            return sw.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            close(sw);
            close(reader);
            close(inputStream);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
                // NOP
            }
        }
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}

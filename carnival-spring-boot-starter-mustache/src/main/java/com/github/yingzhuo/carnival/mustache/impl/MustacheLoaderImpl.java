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
import com.github.yingzhuo.carnival.mustache.MustacheLoader;
import com.github.yingzhuo.carnival.mustache.MustacheLoadingException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author 应卓
 */
public class MustacheLoaderImpl implements MustacheLoader {

    private final String encoding;
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

    public MustacheLoaderImpl(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String load(String location, Map<String, Object> scopes) {
        try {
            Resource resource = resourceLoader.getResource(location);
            Mustache mustache = mustacheFactory.compile(new InputStreamReader(resource.getInputStream(), encoding), resource.getFilename());
            StringWriter writer = new StringWriter();
            mustache.execute(writer, scopes);
            return writer.toString();
        } catch (IOException e) {
            throw new MustacheLoadingException(e);
        }
    }

}

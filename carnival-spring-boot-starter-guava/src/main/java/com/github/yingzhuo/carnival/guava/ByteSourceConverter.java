/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.guava;

import com.google.common.base.Strings;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.3.4
 */
public class ByteSourceConverter extends AbstractBase implements Converter<String, ByteSource> {

    @Override
    public ByteSource convert(String location) {

        if (Strings.isNullOrEmpty(location)) {
            return ByteSource.empty();
        }

        Resource resource = null;

        try {
            resource = loadResource(location);
            return Files.asByteSource(resource.getFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            close(resource);
        }
    }

}

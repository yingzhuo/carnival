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

import com.google.common.base.Preconditions;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.1.10
 */
public final class ResourceText {

    public static String load(String location) {
        return load(location, StandardCharsets.UTF_8);
    }

    public static String load(String location, Charset charset) {
        try {
            Preconditions.checkArgument(location != null);
            val option = ResourceOptional.of(location);
            val lines = IOUtils.readLines(option.getInputStream(), charset);
            val result = StringUtils.join(lines, "");
            option.closeResource();
            return result;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ResourceText() {
    }

}

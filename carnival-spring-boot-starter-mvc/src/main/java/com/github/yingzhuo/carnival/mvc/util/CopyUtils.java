/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.33
 */
public final class CopyUtils {

    private CopyUtils() {
    }

    public static Path copyToTemp(MultipartFile file) {
        Objects.requireNonNull(file);

        try {
            final Path path = Files.createTempFile("", "." + FilenameUtils.getExtension(file.getOriginalFilename()))
                    .normalize();
            FileUtils.copyToFile(file.getInputStream(), path.toFile());
            return path;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

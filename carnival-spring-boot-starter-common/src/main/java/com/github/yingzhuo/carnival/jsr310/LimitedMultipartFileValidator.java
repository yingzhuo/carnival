/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr310;

import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author 应卓
 */
public class LimitedMultipartFileValidator implements ConstraintValidator<LimitedMultipartFile, MultipartFile> {

    private Stream<String> allowedExtensions = null;

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        val filename = value.getOriginalFilename();

        // double check
        if (filename == null) {
            return false;
        }

        val extension = getExtension(filename);
        return allowedExtensions.anyMatch(e -> e.equalsIgnoreCase(extension));
    }

    @Override
    public void initialize(LimitedMultipartFile annotation) {
        this.allowedExtensions = Arrays.stream(annotation.allowedExtensions());
    }

    private String getExtension(String filename) {
        val lastIndex = filename.lastIndexOf(".");

        String extension;

        if (lastIndex == -1 || lastIndex == 0) {
            extension = "";
        } else {
            extension = filename.substring(lastIndex);
        }

        return extension;
    }
}

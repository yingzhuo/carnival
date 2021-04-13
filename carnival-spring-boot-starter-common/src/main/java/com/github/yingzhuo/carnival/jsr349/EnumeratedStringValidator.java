/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.8.2
 */
public class EnumeratedStringValidator implements ConstraintValidator<EnumeratedString, CharSequence> {

    private final Set<String> options = new HashSet<>();
    private boolean caseSensitive = false;

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if (caseSensitive) {
            for (String v : options) {
                if (StringUtils.equals(v, value)) {
                    return true;
                }
            }
        } else {
            for (String v : options) {
                if (StringUtils.equalsIgnoreCase(v, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(EnumeratedString annotation) {
        Collections.addAll(this.options, annotation.value());
        this.caseSensitive = annotation.caseSensitive();
    }

}

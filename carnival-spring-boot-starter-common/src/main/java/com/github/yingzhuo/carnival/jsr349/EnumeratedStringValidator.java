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

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.8.2
 */
public class EnumeratedStringValidator implements ConstraintValidator<EnumeratedString, CharSequence> {

    private static final Map<String, Set<String>> cache = new HashMap<>();

    private Set<String> options = null;
    private boolean caseSensitive = false;

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if (options == null || options.isEmpty()) {
            return false;
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
    @SneakyThrows
    public void initialize(EnumeratedString annotation) {
        val factoryType = annotation.value();

        Set<String> set = null;

        if (annotation.cache()) {
            set = cache.get(factoryType.toString());
        }

        if (set != null) {
            this.options = set;
        } else {
            val factory = factoryType.newInstance();
            this.options = factory.set();

            if (annotation.cache()) {
                cache.put(factoryType.toString(), options);
            }
        }

        this.caseSensitive = annotation.caseSensitive();
    }

}

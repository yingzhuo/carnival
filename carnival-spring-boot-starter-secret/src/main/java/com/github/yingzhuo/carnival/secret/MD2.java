/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import com.github.yingzhuo.carnival.secret.autoconfig.SecretAutoConfig;
import com.github.yingzhuo.carnival.secret.support.NopStringFormatter;
import com.github.yingzhuo.carnival.spring.ProfileUtils;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
public interface MD2 {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Encrypting {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Encrypting> {
            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Encrypting annotation, Class<?> fieldType) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(Encrypting annotation, Class<?> fieldType) {
                String disabledInProfile = SpringUtils.getBean(SecretAutoConfig.SecretProps.class).getDisabledInProfile();
                if (StringUtils.isNotBlank(disabledInProfile) && ProfileUtils.anyActive(disabledInProfile)) {
                    return NopStringFormatter.INSTANCE;
                }

                return (Parser<String>) (s, locale) -> DigestUtils.md2Hex(s);
            }
        }
    }

}

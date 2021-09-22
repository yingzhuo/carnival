/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.converter;

import com.github.yingzhuo.carnival.jpa.util.Keywords;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 * @see NormalizedKeyword
 * @since 1.10.22
 */
public class NormalizedKeywordAnnotationFormatterFactory implements AnnotationFormatterFactory<NormalizedKeyword> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(String.class);
    }

    @Override
    public Printer<?> getPrinter(NormalizedKeyword annotation, Class<?> fieldType) {
        return (Printer<String>) (object, locale) -> object;
    }

    @Override
    public Parser<?> getParser(NormalizedKeyword annotation, Class<?> fieldType) {
        return (Parser<String>) (text, locale) -> Keywords.normalize(text);
    }

}

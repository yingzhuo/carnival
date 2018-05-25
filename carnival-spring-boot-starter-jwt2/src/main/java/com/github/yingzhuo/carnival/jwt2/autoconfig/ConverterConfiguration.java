/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt2.autoconfig;

import com.github.yingzhuo.carnival.jwt2.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 */
public class ConverterConfiguration {

    @Autowired
    public void config(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(new StringToSignatureAlgorithmConverter());
    }

    private static class StringToSignatureAlgorithmConverter implements Converter<String, SignatureAlgorithm> {
        @Override
        public SignatureAlgorithm convert(String source) {
            return SignatureAlgorithm.valueOf(source);
        }
    }

}

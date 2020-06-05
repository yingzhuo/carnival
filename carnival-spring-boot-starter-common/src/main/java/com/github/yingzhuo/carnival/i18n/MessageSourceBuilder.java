/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.14
 */
public final class MessageSourceBuilder {

    private String[] basenames;
    private Charset encoding = StandardCharsets.UTF_8;
    private boolean fallbackToSystemLocale = true;
    private boolean useCodeAsDefaultMessage = false;
    private MessageSourceBuilder() {
    }

    public static MessageSourceBuilder newInstance() {
        return new MessageSourceBuilder();
    }

    public MessageSourceBuilder basenames(String... basenames) {
        this.basenames = basenames;
        return this;
    }

    public MessageSourceBuilder charset(Charset encoding) {
        this.encoding = encoding;
        return this;
    }

    public MessageSourceBuilder fallbackToSystemLocale(boolean fallbackToSystemLocale) {
        this.fallbackToSystemLocale = fallbackToSystemLocale;
        return this;
    }

    public MessageSourceBuilder useCodeAsDefaultMessage(boolean useCodeAsDefaultMessage) {
        this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
        return this;
    }

    public MessageSource build() {
        final Set<String> names = new HashSet<>(Arrays.asList(basenames));
        names.add("classpath:com/github/yingzhuo/carnival/jsr349/ValidationMessages");
        names.add("classpath:com/github/yingzhuo/carnival/localization/china/jsr349/ValidationMessages");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(encoding.displayName());
        messageSource.setBasenames(names.toArray(new String[0]));
        messageSource.setFallbackToSystemLocale(fallbackToSystemLocale);
        messageSource.setUseCodeAsDefaultMessage(useCodeAsDefaultMessage);
        messageSource.setCacheSeconds(-1);
        return messageSource;
    }

}

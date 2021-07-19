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
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.14
 */
public final class MessageSourceBuilder {

    private String[] basenames = new String[0];
    private Charset encoding = UTF_8;
    private boolean fallbackToSystemLocale = true;
    private boolean useCodeAsDefaultMessage = false;
    private Locale defaultLocale = null;
    private Duration cacheTimeout = null;

    private MessageSourceBuilder() {
    }

    public static MessageSourceBuilder newInstance() {
        return new MessageSourceBuilder();
    }

    public MessageSourceBuilder basenames(String... basenames) {
        this.basenames = basenames;
        return this;
    }

    public MessageSourceBuilder encoding(Charset encoding) {
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

    public MessageSourceBuilder defaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    public MessageSourceBuilder cacheTimeout(Duration cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
        return this;
    }

    public MessageSource build() {
        final Set<String> names = new HashSet<>(Arrays.asList(basenames));
        names.add("classpath:com/github/yingzhuo/carnival/jsr380/ValidationMessages");
        names.add("classpath:com/github/yingzhuo/carnival/localization/china/jsr380/ValidationMessages");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(encoding.displayName());
        messageSource.setBasenames(names.toArray(new String[0]));
        messageSource.setFallbackToSystemLocale(fallbackToSystemLocale);
        messageSource.setUseCodeAsDefaultMessage(useCodeAsDefaultMessage);
        messageSource.setDefaultLocale(defaultLocale);
        messageSource.setCacheSeconds(cacheTimeout == null ? -1 : (int) cacheTimeout.getSeconds());
        return messageSource;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.text;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 1.10.27
 */
public class StringFactoryBean implements FactoryBean<String>, InitializingBean {

    private String text;
    private boolean trim = false;
    private boolean toOneLine = false;
    private boolean toUpperCase = false;
    private boolean toLowerCase = false;

    public StringFactoryBean() {
    }

    public StringFactoryBean(String text) {
        this.text = text;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(text, "text is null");
        if (trim) {
            text = text.trim();
        }
        if (toOneLine) {
            text = text.replaceAll("\r*\n*", "");
        }
        if (toLowerCase) {
            text = text.toLowerCase();
        }
        if (toUpperCase) {
            text = text.toUpperCase();
        }
    }

    @Override
    public String getObject() throws Exception {
        return text;
    }

    @Override
    public final Class<?> getObjectType() {
        return String.class;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    public void setToOneLine(boolean toOneLine) {
        this.toOneLine = toOneLine;
    }

    public void setToUpperCase(boolean toUpperCase) {
        this.toUpperCase = toUpperCase;
    }

    public void setToLowerCase(boolean toLowerCase) {
        this.toLowerCase = toLowerCase;
    }

}

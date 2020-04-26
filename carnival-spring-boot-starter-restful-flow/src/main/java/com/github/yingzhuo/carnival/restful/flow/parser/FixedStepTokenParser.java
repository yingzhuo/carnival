/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.0
 */
@Slf4j
public class FixedStepTokenParser implements StepTokenParser, InitializingBean {

    private final String tokenValue;
    private final int order;

    public FixedStepTokenParser(String tokenValue) {
        this(tokenValue, 0);
    }

    public FixedStepTokenParser(String tokenValue, int order) {
        this.tokenValue = Objects.requireNonNull(tokenValue);
        this.order = order;
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        return Optional.of(tokenValue);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}

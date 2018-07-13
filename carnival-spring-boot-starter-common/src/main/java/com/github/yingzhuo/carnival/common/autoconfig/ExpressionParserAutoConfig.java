package com.github.yingzhuo.carnival.common.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class ExpressionParserAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

}

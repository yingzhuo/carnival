/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.mvc;

import com.github.yingzhuo.carnival.restful.security.annotation.SecurityContext;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.7.7
 */
public class AnnotationSecurityContextSupport implements HandlerMethodArgumentResolver {

    private final ExpressionParser expressionResolver =
            new SpelExpressionParser(new SpelParserConfiguration(true, true));

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SecurityContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        val spel = Objects.requireNonNull(parameter.getParameterAnnotation(SecurityContext.class)).value();

        if (spel.isEmpty()) return null;

        val context = new StandardEvaluationContext(RestfulSecurityContext.current()); // 根对象: RestfulSecurityContext
        val exp = expressionResolver.parseExpression(spel);
        return exp.getValue(context);
    }

}

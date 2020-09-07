/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.auth;

import com.github.yingzhuo.carnival.restful.security.Requires;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.restful.security.util.TokenUtils;
import com.github.yingzhuo.carnival.restful.security.util.UserDetailsUtils;
import lombok.val;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author 应卓
 * @since 1.7.7
 */
public class RequiresAuthComponent implements AuthenticationComponent<Requires> {

    private final ExpressionParser expressionResolver =
            new SpelExpressionParser(new SpelParserConfiguration(true, true));

    @Override
    public void authenticate(Token token, UserDetails userDetails, Requires annotation) throws RestfulSecurityException {
        val spel = annotation.value();

        val context = new StandardEvaluationContext();
        context.setVariable("userDetails", UserDetailsUtils.get());
        context.setVariable("token", TokenUtils.get());

        val exp = expressionResolver.parseExpression(spel);

        if (!exp.getValue(context, Boolean.class)) {
            throw new AuthenticationException(RestfulSecurityContext.getRequest());
        }
    }

}

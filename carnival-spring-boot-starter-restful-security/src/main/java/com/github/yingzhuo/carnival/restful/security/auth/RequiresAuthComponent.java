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
import com.github.yingzhuo.carnival.restful.security.exception.SpelException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
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
        try {
            doAuthenticate(token, userDetails, annotation);
        } catch (ParseException | EvaluationException e) {
            throw new SpelException(e.getMessage());
        }
    }

    public void doAuthenticate(Token token, UserDetails userDetails, Requires annotation) throws RestfulSecurityException {
        val spel = annotation.value();

        // TODO: 要考虑到表达式不是Boolean结果的情况，要抓异常并处理妥当
        val context = new StandardEvaluationContext(RestfulSecurityContext.current());
        val exp = expressionResolver.parseExpression(spel);
        val res = exp.getValue(context, Boolean.class);

        if (res == null || !res) {
            throw new AuthenticationException(RestfulSecurityContext.current().getRequest());
        }
    }

}

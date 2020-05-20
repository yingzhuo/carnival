/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.core;

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeated;
import com.github.yingzhuo.carnival.restful.norepeated.exception.NoTokenFoundException;
import com.github.yingzhuo.carnival.restful.norepeated.exception.RepeatedRequestException;
import com.github.yingzhuo.carnival.restful.norepeated.parser.NoRepeatedTokenParser;
import lombok.val;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NoRepeatedInterceptor extends AbstractHandlerInterceptorSupport {

    private NoRepeatedTokenParser tokenParser;
    private ExceptionTransformer exceptionTransformer;

    private final StringRedisTemplate template;

    public NoRepeatedInterceptor(RedisConnectionFactory connectionFactory) {
        this.template = new StringRedisTemplate(connectionFactory);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            doPreHandle(request, response, handler);
        } catch (Exception e) {
            if (exceptionTransformer != null) {
                throw exceptionTransformer.transform(e).orElse(e);
            }
            throw e;
        }

        return true;
    }

    public void doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 查找元注释NoRepeated
        // 没有元注释直接退出
        val annotation = getToken(handler);
        if (annotation == null) {
            return;
        }

        val tokenOption = tokenParser.parse(new ServletWebRequest(request, response));

        // 解析不出Token则抛出异常退出
        if (!tokenOption.isPresent()) {
            throw new NoTokenFoundException(request);
        }

        String count = template.opsForValue().get(tokenOption.get());
        if ("1".equals(count)) {
            template.delete(tokenOption.get());
        } else {
            throw new RepeatedRequestException(request);
        }
    }

    private NoRepeated getToken(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }

        return super.getMethodOrClassAnnotation(NoRepeated.class, handler).orElse(null);
    }

    public void setExceptionTransformer(ExceptionTransformer exceptionTransformer) {
        this.exceptionTransformer = exceptionTransformer;
    }

    public void setTokenParser(NoRepeatedTokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

}

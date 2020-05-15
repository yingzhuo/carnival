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

import com.github.yingzhuo.carnival.common.mvc.HandlerInterceptorSupport;
import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.jedis.util.JedisUtils;
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeated;
import com.github.yingzhuo.carnival.restful.norepeated.exception.NoTokenFoundException;
import com.github.yingzhuo.carnival.restful.norepeated.exception.RepeatedRequestException;
import com.github.yingzhuo.carnival.restful.norepeated.parser.NoRepeatedTokenParser;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NoRepeatedInterceptor extends HandlerInterceptorSupport {

    private NoRepeatedTokenParser tokenParser;
    private ExceptionTransformer exceptionTransformer;

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
            throw new NoTokenFoundException(getMessage(annotation));
        }

        val commands = JedisUtils.getJedisCommands();

        try {
            String count = commands.get(tokenOption.get());

            if ("1".equals(count)) {
                commands.del(tokenOption.get());
            } else {
                throw new RepeatedRequestException(getMessage(annotation));
            }
        } finally {
            JedisUtils.close(commands);
        }
    }

    private NoRepeated getToken(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }

        Optional<NoRepeated> token = super.getMethodAnnotation(NoRepeated.class, handler);
        if (token.isPresent()) {
            return token.get();
        }

        token = super.getClassAnnotation(NoRepeated.class, handler);
        return token.orElse(null);
    }

    private String getMessage(NoRepeated annotation) {
        val msg = annotation.message();
        return "".equals(msg) ? null : msg;
    }

    public void setExceptionTransformer(ExceptionTransformer exceptionTransformer) {
        this.exceptionTransformer = exceptionTransformer;
    }

    public void setTokenParser(NoRepeatedTokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

}

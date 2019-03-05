/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.apigateway.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
public class LoggingFilter extends AbstractZuulFilter {

    @Override
    protected void doRun(RequestContext requestContext) {
        try {
            doLog(requestContext.getRequest());
        } catch (Exception e) {
            // NOP
        }
    }

    private void doLog(HttpServletRequest request) {
        log.debug(StringUtils.repeat('-', 120));

        log.debug("[Path]: ");
        log.debug("\t\t\t{}", request.getRequestURI());

        log.debug("[Method]: ");
        log.debug("\t\t\t{}", request.getMethod());

        log.debug("[Headers]: ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            log.debug("\t\t\t{} = {}", name, name.equalsIgnoreCase("cookie") ? StringUtils.abbreviate(value, 60) : value);
        }

        log.debug("[Params]: ");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            log.debug("\t\t\t{} = {}", name, value);
        }

        log.debug(StringUtils.repeat('-', 120));
    }
}

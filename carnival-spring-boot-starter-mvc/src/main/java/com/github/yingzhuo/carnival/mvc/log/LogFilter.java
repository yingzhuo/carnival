/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.log;

import com.github.yingzhuo.carnival.mvc.filter.AbstractServletFilter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求级日期记录器
 *
 * @author 应卓
 * @since 1.9.14
 */
@Slf4j
class LogFilter extends AbstractServletFilter {

    public LogFilter() {
        super.setSkipPatterns("/favicon.ico", "/actuator", "/actuator/**");
    }

    @Override
    protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) {
        try {
            doLog(request);
        } catch (Exception e) {
            // NOP
        }
        return true;
    }

    private void doLog(HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug(StringUtils.repeat('-', 150));
            log.debug("Method: {}", request.getMethod());
            log.debug("Path: {}", request.getRequestURI());
            log.debug("Requests:");
            val requestParamNames = request.getParameterNames();
            while (requestParamNames.hasMoreElements()) {
                val requestParamName = requestParamNames.nextElement();
                val requestParamValue = request.getParameter(requestParamName);
                log.debug("\t\t{} = {}", requestParamName, requestParamValue);
            }

            log.debug("Headers:");
            val requestHeaderNames = request.getHeaderNames();
            while (requestHeaderNames.hasMoreElements()) {
                val requestHeaderName = requestHeaderNames.nextElement();
                val requestHeaderValue = request.getHeader(requestHeaderName);
                log.debug("\t\t{} = {}", requestHeaderName, requestHeaderValue);
            }
            log.debug(StringUtils.repeat('-', 150));
        }
    }

}

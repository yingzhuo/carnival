/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class VeryFirstServletFilter extends AbstractServletFilter {

    @Override
    protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) {
        try {
            execute(request, response);
        } catch (Throwable e) {
            // 其实这里不太可能抛出异常
        }
        return true;
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) {
        VeryFirstServletContext.setRequest(request);
        VeryFirstServletContext.setResponse(response);
    }

    @Override
    public void destroy() {
        VeryFirstServletContext.clean();
    }

}

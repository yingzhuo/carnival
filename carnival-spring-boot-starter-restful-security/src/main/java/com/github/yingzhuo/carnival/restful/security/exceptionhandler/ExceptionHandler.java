/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.exceptionhandler;

import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.22
 */
public interface ExceptionHandler {

    public void handler(HttpServletRequest request, HttpServletResponse httpServletResponse, RestfulSecurityException ex);

    public void handler(HttpServletRequest request, HttpServletResponse httpServletResponse, Exception ex);

}

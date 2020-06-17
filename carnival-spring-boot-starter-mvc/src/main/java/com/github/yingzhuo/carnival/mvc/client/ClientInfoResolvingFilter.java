/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.client;

import com.github.yingzhuo.carnival.mvc.filter.AbstractServletFilter;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.14
 */
public class ClientInfoResolvingFilter extends AbstractServletFilter {

    private final ClientOSTypeResolver clientOSTypeResolver;
    private final ClientOSVersionResolver clientOSVersionResolver;
    private final ClientAppVersionResolver clientAppVersionResolver;
    private final ClientUsingBackendVersionResolver clientUsingBackendVersionResolver;

    public ClientInfoResolvingFilter(ClientOSTypeResolver clientOSTypeResolver, ClientOSVersionResolver clientOSVersionResolver, ClientAppVersionResolver clientAppVersionResolver, ClientUsingBackendVersionResolver clientUsingBackendVersionResolver) {
        this.clientOSTypeResolver = Objects.requireNonNull(clientOSTypeResolver);
        this.clientOSVersionResolver = Objects.requireNonNull(clientOSVersionResolver);
        this.clientAppVersionResolver = Objects.requireNonNull(clientAppVersionResolver);
        this.clientUsingBackendVersionResolver = Objects.requireNonNull(clientUsingBackendVersionResolver);
    }

    @Override
    protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletWebRequest webRequest = new ServletWebRequest(request);
        ClientInfoContext.clean();
        ClientInfoContext.setClientOsType(clientOSTypeResolver.resolveClientOSType(webRequest));
        ClientInfoContext.setClientOsVersion(clientOSVersionResolver.resolveClientOSVersion(webRequest));
        ClientInfoContext.setClientAppVersion(clientAppVersionResolver.resolveClientAppVersion(webRequest));
        ClientInfoContext.setClientUsingBackendVersion(clientUsingBackendVersionResolver.resolveClientUsingBackendVersion(webRequest));
        return true;
    }

}

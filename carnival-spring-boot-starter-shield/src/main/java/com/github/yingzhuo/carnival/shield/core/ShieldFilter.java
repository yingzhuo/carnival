/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.core;

import com.github.yingzhuo.carnival.shield.algorithm.Algorithm;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author 应卓
 * @since 1.6.21
 */
public class ShieldFilter extends AbstractShieldFilter {

    private final Algorithm algorithm;
    private final Charset charset;

    public ShieldFilter(RequestMappingHandlerMapping mappings, Algorithm algorithm, Charset charset) {
        super(mappings);
        this.algorithm = algorithm;
        this.charset = charset;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final HandlerMethod handlerMethod = super.getHandlerMethod(request);
        if (handlerMethod == null) {
            chain.doFilter(request, response);
            return;
        }

        boolean encryptionStatus = shouldEncrypt(handlerMethod);
        boolean decryptionStatus = shouldDecrypt(handlerMethod);

        if (!encryptionStatus && !decryptionStatus) {
            chain.doFilter(request, response);
            return;
        }

        final DecryptionRequest requestWrapper = new DecryptionRequest(request);
        final EncryptionResponse responseWrapper = new EncryptionResponse(response);

        if (decryptionStatus) {
            processDecryption(requestWrapper, request);
        }

        // 同时需要加解密
        if (encryptionStatus && decryptionStatus) {
            chain.doFilter(requestWrapper, responseWrapper);
        } else if (encryptionStatus) {
            // 只需要响应加密
            chain.doFilter(request, responseWrapper);
        } else {
            // 只需要请求解密
            chain.doFilter(requestWrapper, response);
        }

        // 配置了需要加密才处理
        if (encryptionStatus) {
            String responseData = responseWrapper.getResponseBodyAsString();
            writeEncryptedContent(responseData, response);
        }
    }

    private void writeEncryptedContent(String responseData, ServletResponse response) throws IOException {
        ServletOutputStream out = null;
        try {
            responseData = algorithm.encrypt(responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding(charset.displayName());
            out = response.getOutputStream();
            out.write(responseData.getBytes(charset));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    private void processDecryption(DecryptionRequest requestWrapper, HttpServletRequest request) {
        final String requestData = requestWrapper.getRequestBodyAsString();
        try {
            if (!StringUtils.endsWithIgnoreCase(request.getMethod(), RequestMethod.GET.name())) {
                String decryptRequestData = algorithm.decrypt(requestData);
                requestWrapper.setRequestBody(decryptRequestData.getBytes(charset));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

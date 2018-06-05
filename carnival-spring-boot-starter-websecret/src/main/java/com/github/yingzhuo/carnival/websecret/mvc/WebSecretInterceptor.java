/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.mvc;

import com.github.yingzhuo.carnival.websecret.dao.SecretLoader;
import com.github.yingzhuo.carnival.websecret.exception.WebSecretException;
import com.github.yingzhuo.carnival.websecret.matcher.SignatureMatcher;
import com.github.yingzhuo.carnival.websecret.parser.ClientIdParser;
import com.github.yingzhuo.carnival.websecret.parser.NonceParser;
import com.github.yingzhuo.carnival.websecret.parser.SignatureParser;
import com.github.yingzhuo.carnival.websecret.parser.TimestampParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 */
@Slf4j
public class WebSecretInterceptor implements HandlerInterceptor {

    private NonceParser nonceParser;

    private ClientIdParser clientIdParser;

    private SignatureParser signatureParser;

    private TimestampParser timestampParser;

    private SecretLoader secretLoader;

    private SignatureMatcher signatureMatcher;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 不处理静态资源
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        NativeWebRequest req = new ServletWebRequest(request);

        String nonce = nonceParser.parse(req);
        log.debug("nonce: {}", nonce);
        if (nonce == null) {
            throw new WebSecretException("Lack of nonce.");
        }

        String timestamp = timestampParser.parse(req);
        log.debug("timestamp: {}", timestamp);
        if (timestamp == null) {
            throw new WebSecretException("Lack of timestamp.");
        }

        String signature = signatureParser.parse(req);
        log.debug("signature: {}", signature);
        if (signature == null) {
            throw new WebSecretException("Lack of signature.");
        }

        String appId = clientIdParser.parse(req);
        log.debug("appId: {}", appId);
        if (appId == null) {
            throw new WebSecretException("Lack of ApplicationId");
        }

        String secret = secretLoader.load(appId);
        log.debug("secret: {}", secret);
        if (secret == null) {
            final String msg = String.format("Cannot load secret for applicationId (%s)", appId);
            throw new WebSecretException(msg);
        }

        if (! signatureMatcher.matches(signature, secret, nonce, timestamp)) {
            throw new WebSecretException("Invalid signature.");
        }

        return true;
    }


    public void setNonceParser(NonceParser nonceParser) {
        this.nonceParser = nonceParser;
    }

    public void setClientIdParser(ClientIdParser clientIdParser) {
        this.clientIdParser = clientIdParser;
    }

    public void setSignatureParser(SignatureParser signatureParser) {
        this.signatureParser = signatureParser;
    }

    public void setTimestampParser(TimestampParser timestampParser) {
        this.timestampParser = timestampParser;
    }

    public void setSecretLoader(SecretLoader secretLoader) {
        this.secretLoader = secretLoader;
    }

    public void setSignatureMatcher(SignatureMatcher signatureMatcher) {
        this.signatureMatcher = signatureMatcher;
    }

}

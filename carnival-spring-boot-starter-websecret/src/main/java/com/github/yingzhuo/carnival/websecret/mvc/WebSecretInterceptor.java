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

import com.github.yingzhuo.carnival.websecret.ValidationStrategy;
import com.github.yingzhuo.carnival.websecret.WebSecret;
import com.github.yingzhuo.carnival.websecret.dao.SecretLoader;
import com.github.yingzhuo.carnival.websecret.exception.WebSecretException;
import com.github.yingzhuo.carnival.websecret.matcher.SignatureMatcher;
import com.github.yingzhuo.carnival.websecret.parser.ClientIdParser;
import com.github.yingzhuo.carnival.websecret.parser.NonceParser;
import com.github.yingzhuo.carnival.websecret.parser.SignatureParser;
import com.github.yingzhuo.carnival.websecret.parser.TimestampParser;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author 应卓
 */
@Slf4j
public class WebSecretInterceptor implements HandlerInterceptor {

    private LocaleResolver localeResolver = new NullLocaleResolver();

    private NonceParser nonceParser;

    private ClientIdParser clientIdParser;

    private SignatureParser signatureParser;

    private TimestampParser timestampParser;

    private SecretLoader secretLoader;

    private SignatureMatcher signatureMatcher;

    private ValidationStrategy validationStrategy = ValidationStrategy.ANNOTATED;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        val method = ((HandlerMethod) handler).getMethod();
        if (method.getAnnotation(WebSecret.class) == null && validationStrategy == ValidationStrategy.ANNOTATED) {
            return true;
        }

        NativeWebRequest req = new ServletWebRequest(request);

        Locale locale = localeResolver.resolveLocale(request);

        String nonce = nonceParser.parse(req, locale).orElse(null);
        log.debug("nonce: {}", nonce);
        if (nonce == null) {
            throw new WebSecretException("Lack of nonce.");
        } else {
            WebSecretHolder.nonceHolder.set(nonce);
        }

        String timestamp = timestampParser.parse(req, locale).orElse(null);
        log.debug("timestamp: {}", timestamp);
        if (timestamp == null) {
            throw new WebSecretException("Lack of timestamp.");
        } else {
            WebSecretHolder.timestampHolder.set(timestamp);
        }

        String signature = signatureParser.parse(req, locale).orElse(null);
        log.debug("signature: {}", signature);
        if (signature == null) {
            throw new WebSecretException("Lack of signature.");
        } else {
            WebSecretHolder.signatureHolder.set(signature);
        }

        String clientId = clientIdParser.parse(req, locale).orElse(null);
        log.debug("appId: {}", clientId);
        if (clientId == null) {
            throw new WebSecretException("Lack of ApplicationId");
        } else {
            WebSecretHolder.clientIdHolder.set(clientId);
        }

        String secret = secretLoader.load(clientId);
        log.debug("secret: {}", secret);
        if (secret == null) {
            final String msg = String.format("Cannot load secret for clientId (%s)", clientId);
            throw new WebSecretException(msg);
        }

        if (!signatureMatcher.matches(signature, secret, nonce, timestamp)) {
            throw new WebSecretException("Invalid signature.");
        }

        return true;
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
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

    public void setValidationStrategy(ValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    // --------------------------------------------------------------------------------------------------------------

    private static class NullLocaleResolver implements LocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            return null;
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
            // 无动作
        }
    }

}

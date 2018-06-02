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
import com.github.yingzhuo.carnival.websecret.parser.AppIdParser;
import com.github.yingzhuo.carnival.websecret.parser.NonceParser;
import com.github.yingzhuo.carnival.websecret.parser.SignatureParser;
import com.github.yingzhuo.carnival.websecret.parser.TimestampParser;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

/**
 * @author 应卓
 */
public class WebSecretInterceptor implements HandlerInterceptor {

    private NonceParser nonceParser;

    private AppIdParser appIdParser;

    private SignatureParser signatureParser;

    private TimestampParser timestampParser;

    private SecretLoader secretLoader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 不处理静态资源
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        NativeWebRequest req = new ServletWebRequest(request);

        String nonce = nonceParser.parse(req);
        if (nonce == null) {
            throw new WebSecretException();
        }

        String timestamp = timestampParser.parse(req);
        if (timestamp == null) {
            throw new WebSecretException();
        }

        String signature = signatureParser.parse(req);
        if (signature == null) {
            throw new WebSecretException();
        }

        String appId = appIdParser.parse(req);
        if (appId == null) {
            throw new WebSecretException();
        }

        String secret = secretLoader.load(appId);
        if (secret == null) {
            throw new WebSecretException();
        }

        String s = sha256Hex(secret + nonce + timestamp);   // 正确的签名

        if (!s.equals(secret)) {
            throw new WebSecretException();
        }

        return true;
    }

    public String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void setNonceParser(NonceParser nonceParser) {
        this.nonceParser = nonceParser;
    }

    public void setAppIdParser(AppIdParser appIdParser) {
        this.appIdParser = appIdParser;
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
}

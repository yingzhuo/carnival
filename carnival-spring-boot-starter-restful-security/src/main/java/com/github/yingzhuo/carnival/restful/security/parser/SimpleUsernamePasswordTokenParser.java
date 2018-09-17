/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.parser;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import lombok.var;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author 应卓
 */
public class SimpleUsernamePasswordTokenParser implements TokenParser {

    private final String usernameParameterName;
    private final String passwordParameterName;
    private Function<String, String> usernameMapper = (x) -> x;
    private Function<String, String> passwordMapper = (x) -> x;

    public SimpleUsernamePasswordTokenParser(String usernameParameterName, String passwordParameterName) {
        this.usernameParameterName = usernameParameterName;
        this.passwordParameterName = passwordParameterName;
    }

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest, Locale locale) {
        var username = webRequest.getParameter(usernameParameterName);
        var password = webRequest.getParameter(passwordParameterName);

        username = usernameMapper.apply(username);
        password = passwordMapper.apply(password);

        if (username != null && password != null) {
            return Optional.of(new UsernamePasswordToken(username, password));
        }

        return Optional.empty();
    }

    public String getUsernameParameterName() {
        return usernameParameterName;
    }

    public String getPasswordParameterName() {
        return passwordParameterName;
    }

    public void setUsernameMapper(Function<String, String> usernameMapper) {
        this.usernameMapper = usernameMapper;
    }

    public void setPasswordMapper(Function<String, String> passwordMapper) {
        this.passwordMapper = passwordMapper;
    }

}

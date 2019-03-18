/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.httpbasic.autoconfig.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.json.Json;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 */
public class JsonHttpBasicAuthFailureHandler implements HttpBasicAuthFailureHandler, InitializingBean {

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());   // 401
        val out = response.getOutputStream();

        val json = Json.newInstance();
        json.code(HttpStatus.UNAUTHORIZED);
        json.errorMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());

        objectMapper.writeValue(out, json);
        out.flush();
        out.close();
    }

    @Override
    public void afterPropertiesSet() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }

}

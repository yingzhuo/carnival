/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody.algorithm;

import com.github.yingzhuo.carnival.restful.security.responsebody.ResponseBodyEncryptingAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.30
 */
public class OddResponseBodyEncryptingAlgorithm implements ResponseBodyEncryptingAlgorithm {

    @Override
    public String encrypt(String body) {
        body = StringUtils.reverse(body);
        body = Base64.getEncoder().encodeToString(body.getBytes(StandardCharsets.UTF_8));
        body = StringUtils.reverse(body);
        return body;
    }

}

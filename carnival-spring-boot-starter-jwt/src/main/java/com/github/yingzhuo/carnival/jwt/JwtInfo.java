/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt;

import lombok.Data;

import java.util.*;

/**
 * @author 应卓
 */
@Data
public final class JwtInfo {

    // Public Claims (Header)

    /* private String type; */

    /* private String contentType; */

    private String keyId;

    // Public Claims (Payload)

    private String issuer;

    private String subject;

    private List<String> audience = new ArrayList<>();

    private Date expiresAt;

    private Date notBefore;

    private Date issuedAt;

    private String jwtId;

    // Private Claims

    private Map<String, Object> privateClaims = new HashMap<>(0);
}

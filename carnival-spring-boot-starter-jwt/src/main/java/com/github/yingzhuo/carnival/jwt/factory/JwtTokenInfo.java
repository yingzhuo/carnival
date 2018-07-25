/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.factory;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
@Getter
@Setter
public class JwtTokenInfo {

    public static Builder builder() {
        return new Builder();
    }

    private JwtTokenInfo() {
        super();
    }

    // Public Claims (Header)
    private String keyId;

    private String issuer;

    private String subject;

    private List<String> audience = new ArrayList<>();

    private Date expiresAt;

    private Date notBefore;

    private Date issuedAt;

    private String jwtId;

    // Private Claims
    private Map<String, Object> privateClaims = new HashMap<>(0);

    public static class Builder {
        private String keyId;
        private String issuer;
        private String subject;
        private List<String> audience = new ArrayList<>();
        private Date expiresAt;
        private Date notBefore;
        private Date issuedAt;
        private String jwtId;
        private Map<String, Object> privateClaims = new HashMap<>(0);

        private Builder() {
            super();
        }

        public Builder keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public Builder issuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder audience(List<String> audience) {
            this.audience = audience;
            return this;
        }

        public Builder audience(String audience) {
            List<String> list = new ArrayList<>(1);
            list.add(audience);
            return audience(list);
        }

        public Builder expiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder notBefore(Date notBefore) {
            this.notBefore = notBefore;
            return this;
        }

        public Builder issuedAt(Date issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public Builder jwtId(String jwtId) {
            return this;
        }

        public Builder putPrivateClaim(String key, Date value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, String value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, String[] value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Integer value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Integer[] value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Long value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Long[] value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Boolean value) {
            return putPrivateClaim(key, Objects.requireNonNull(value).toString());
        }

        public Builder putPrivateClaim(String key, Boolean[] value) {
            return putPrivateClaim(key, Arrays.stream(Objects.requireNonNull(value)).map(Object::toString).collect(Collectors.toList()).toArray(new String[value.length]));
        }

        private Builder putPrivateClaim(String key, Object value) {
            this.privateClaims.put(key, value);
            return this;
        }

        public JwtTokenInfo build() {
            JwtTokenInfo info = new JwtTokenInfo();
            info.keyId = this.keyId;
            info.issuer = this.issuer;
            info.subject = this.subject;
            info.audience = this.audience;
            info.expiresAt = this.expiresAt;
            info.notBefore = this.notBefore;
            info.issuedAt = this.issuedAt;
            info.privateClaims = this.privateClaims;
            return info;
        }

    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.factory;

import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
@Getter
public class JwtTokenInfo implements Serializable {

    private String keyId;
    private String issuer;
    private String subject;
    private String[] audience;
    private Date expiresAt;
    private Date notBefore;
    private Date issuedAt;
    private String jwtId;
    private Map<String, Object> privateClaims = new HashMap<>();

    private JwtTokenInfo() {
    }

    public static Builder builder() {
        return new Builder();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class Builder {
        private String keyId;
        private String issuer;
        private String subject;
        private String[] audience;
        private Date expiresAt;
        private Date notBefore;
        private Date issuedAt;
        private String jwtId;
        private Map<String, Object> privateClaims = new HashMap<>(0);

        private Builder() {
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

        public Builder audience(String... audience) {
            this.audience = audience;
            return this;
        }

        public Builder expiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder expiresAtFuture(long duration, TimeUnit timeUnit) {
            return expiresAt(afterNow(duration, timeUnit));
        }

        public Builder expiresAtFuture(Duration duration) {
            return expiresAtFuture(duration.toMillis(), TimeUnit.MILLISECONDS);
        }

        public Builder notBefore(Date notBefore) {
            this.notBefore = notBefore;
            return this;
        }

        public Builder notBeforeFuture(long duration, TimeUnit timeUnit) {
            return notBefore(afterNow(duration, timeUnit));
        }

        public Builder notBeforeFuture(Duration duration) {
            return notBeforeFuture(duration.toMillis(), TimeUnit.MILLISECONDS);
        }

        public Builder issuedAt(Date issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public Builder issuedAtNow() {
            return issuedAt(new Date());
        }

        public Builder jwtId(String jwtId) {
            this.jwtId = jwtId;
            return this;
        }

        public Builder putRandomPrivateClaim() {
            this.putPrivateClaim("__random__", UUID.randomUUID().toString().replaceAll("-", ""));
            return this;
        }

        public Builder putPrivateClaim(String key, Boolean value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Date value) {
            return putPrivateClaim(key, (Object) value);
        }

        public Builder putPrivateClaim(String key, Double value) {
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

        private Builder putPrivateClaim(String key, Object value) {
            this.privateClaims.put(
                    Objects.requireNonNull(key),
                    Objects.requireNonNull(value));
            return this;
        }

        private Date afterNow(long duration, TimeUnit timeUnit) {
            Objects.requireNonNull(timeUnit);
            return new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
        }

        private Date beforeNow(long duration, TimeUnit timeUnit) {
            Objects.requireNonNull(timeUnit);
            return new Date(System.currentTimeMillis() - timeUnit.toMillis(duration));
        }

        public JwtTokenInfo build() {
            JwtTokenInfo info = new JwtTokenInfo();
            info.jwtId = this.jwtId;
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

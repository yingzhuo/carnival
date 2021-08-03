/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.factory;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.10.2
 */
public final class JwtTokenMetadata implements Serializable {

    private String keyId;
    private String issuer;
    private String subject;
    private List<String> audience = new ArrayList<>();
    private Date expiresAt;
    private Date notBefore;
    private Date issuedAt;
    private String jwtId;
    private Map<String, Object> payloadClaims = new HashMap<>(0);

    private JwtTokenMetadata() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getAudience() {
        return audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getJwtId() {
        return jwtId;
    }

    public void setJwtId(String jwtId) {
        this.jwtId = jwtId;
    }

    public Map<String, Object> getPayloadClaims() {
        return payloadClaims;
    }

    public void setPayloadClaims(Map<String, Object> payloadClaims) {
        this.payloadClaims = payloadClaims;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtTokenMetadata that = (JwtTokenMetadata) o;
        return Objects.equals(keyId, that.keyId) &&
                Objects.equals(issuer, that.issuer) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(audience, that.audience) &&
                Objects.equals(expiresAt, that.expiresAt) &&
                Objects.equals(notBefore, that.notBefore) &&
                Objects.equals(issuedAt, that.issuedAt) &&
                Objects.equals(jwtId, that.jwtId) &&
                Objects.equals(payloadClaims, that.payloadClaims);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyId, issuer, subject, audience, expiresAt, notBefore, issuedAt, jwtId, payloadClaims);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class Builder {
        private final Map<String, Object> payloadClaims = new HashMap<>();
        private String keyId;
        private String issuer;
        private String subject;
        private List<String> audience = new ArrayList<>();
        private Date expiresAt;
        private Date notBefore;
        private Date issuedAt;
        private String jwtId;

        private Builder() {
        }

        public Builder keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public Builder keyId(Supplier<String> supplier) {
            return keyId(supplier.get());
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

        public Builder audience(String... audience) {
            return audience(Arrays.asList(audience));
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
            return notBefore(this.afterNow(duration, timeUnit));
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

        public Builder jwtId(Supplier<String> supplier) {
            return jwtId(supplier.get());
        }

        public Builder putPayloadClaim(String key, Boolean value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Date value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Double value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, String value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, String[] value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Integer value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Integer[] value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Long value) {
            return doPutPayloadClaim(key, value);
        }

        public Builder putPayloadClaim(String key, Long[] value) {
            return doPutPayloadClaim(key, value);
        }

        private Builder doPutPayloadClaim(String key, Object value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            this.payloadClaims.put(key, value);
            return this;
        }

        public JwtTokenMetadata build() {
            JwtTokenMetadata meta = new JwtTokenMetadata();
            meta.jwtId = this.jwtId;
            meta.keyId = this.keyId;
            meta.issuer = this.issuer;
            meta.subject = this.subject;
            meta.audience = this.audience;
            meta.expiresAt = this.expiresAt;
            meta.notBefore = this.notBefore;
            meta.issuedAt = this.issuedAt;
            meta.payloadClaims = this.payloadClaims;
            return meta;
        }

        private Date afterNow(long duration, TimeUnit timeUnit) {
            Objects.requireNonNull(timeUnit);
            return new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
        }
    }

}

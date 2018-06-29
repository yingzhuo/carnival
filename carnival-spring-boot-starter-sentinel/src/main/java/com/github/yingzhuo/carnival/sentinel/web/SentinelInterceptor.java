/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel.web;

import com.github.yingzhuo.carnival.sentinel.Sentinel;
import com.github.yingzhuo.carnival.sentinel.exception.AccessDeniedException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public class SentinelInterceptor implements HandlerInterceptor {

    private static final String REDIS_KEY_SCOPE = SentinelInterceptor.class.getSimpleName() + ":";

    private final StringRedisTemplate redisTemplate;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final Map<String, Sentinel> patternSentinelMap;

    public SentinelInterceptor(StringRedisTemplate redisTemplate, Map<String, Sentinel> patternSentinelMap) {
        this.patternSentinelMap = patternSentinelMap;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (patternSentinelMap.isEmpty()) {
            return true;
        }

        final String ip = getIp(request);

        if (!StringUtils.hasText(ip)) {
            return true;
        }

        final String path = request.getRequestURI();

        findSentinel(path).ifPresent(it -> {
            checkMinAccessIntervalInMillis(ip, it);
            checkMaxAccessCount(ip, it);
        });

        return true;
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    private void checkMinAccessIntervalInMillis(String ip, Sentinel sentinel) {
        if (sentinel.getMinAccessIntervalInMillis() < 0) {
            return;
        }

        final String key = REDIS_KEY_SCOPE + "LAST_ACCESS:" + ip;

        try {

            String lastAccessString = redisTemplate.opsForValue().get(key);

            if (lastAccessString == null) {
                return;
            }

            Long lastAccess = Long.parseLong(lastAccessString);

            if (!(System.currentTimeMillis() - lastAccess > sentinel.getMinAccessIntervalInMillis())) {
                throw new AccessDeniedException();
            }

        } finally {

            // 更新最后访问时间
            long now = System.currentTimeMillis();
            redisTemplate.opsForValue().set(key, String.valueOf(now));
        }
    }

    private void checkMaxAccessCount(String ip, Sentinel sentinel) {
        if (sentinel.getMaxAccessCount() < 0) {
            return;
        }

        final String k1 = REDIS_KEY_SCOPE + "ACCESS_COUNT:" + ip;
        final String k2 = REDIS_KEY_SCOPE + "ACCESS_COUNT_FIRST:" + ip;

        final String countString = redisTemplate.opsForValue().get(k1);

        if (countString == null) {
            long now = System.currentTimeMillis();
            redisTemplate.opsForValue().set(k1, "1", sentinel.getMaxAccessCountTimeUnit().toMillis(1) * sentinel.getMaxAccessCountTimeUnitNumber(), TimeUnit.MILLISECONDS);
            redisTemplate.opsForValue().set(k2, String.valueOf(now), sentinel.getMaxAccessCountTimeUnit().toMillis(1) * sentinel.getMaxAccessCountTimeUnitNumber(), TimeUnit.MILLISECONDS);
            return;
        }

        long count = Long.parseLong(countString);

        try {

            if (count >= sentinel.getMaxAccessCount()) {
                throw new AccessDeniedException();
            }

        } finally {
            count += 1;
            long first = Long.parseLong(redisTemplate.opsForValue().get(k2));
            redisTemplate.opsForValue().set(k1, String.valueOf(count), sentinel.getMaxAccessCountTimeUnit().toMillis(1) * sentinel.getMaxAccessCountTimeUnitNumber() - (System.currentTimeMillis() - first), TimeUnit.MILLISECONDS);
        }

    }

    private Optional<Sentinel> findSentinel(String path) {

        for (String pattern : patternSentinelMap.keySet()) {
            if (pathMatcher.match(pattern, path)) {
                return Optional.of(patternSentinelMap.get(pattern));
            }
        }

        return Optional.empty();
    }

}

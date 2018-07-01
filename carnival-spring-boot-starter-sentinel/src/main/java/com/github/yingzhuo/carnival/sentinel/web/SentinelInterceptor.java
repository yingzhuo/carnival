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

import com.github.yingzhuo.carnival.sentinel.LimitedAccess;
import com.github.yingzhuo.carnival.sentinel.exception.AccessDeniedException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public class SentinelInterceptor implements HandlerInterceptor {

    private static final String REDIS_KEY_SCOPE = SentinelInterceptor.class.getSimpleName() + ":";

    private final StringRedisTemplate redisTemplate;

    public SentinelInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        final Method method = ((HandlerMethod) handler).getMethod();
        final LimitedAccess annotation = method.getAnnotation(LimitedAccess.class);

        if (annotation == null) {
            return true;
        }

        final String ip = getIp(request);

        if (!StringUtils.hasText(ip)) {
            return true;
        }

        checkMinAccessIntervalInMillis(ip, annotation);
        checkMaxAccessCount(ip, annotation);

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

    private void checkMinAccessIntervalInMillis(String ip, LimitedAccess annotation) {
        if (annotation.minAccessIntervalInMillis() < 0) {
            return;
        }

        final String key = REDIS_KEY_SCOPE + "LAST_ACCESS:" + ip;

        try {

            final String lastAccessString = redisTemplate.opsForValue().get(key);

            if (lastAccessString == null) {
                return;
            }

            Long lastAccess = Long.parseLong(lastAccessString);

            if (!(System.currentTimeMillis() - lastAccess > annotation.minAccessIntervalInMillis())) {
                throw new AccessDeniedException();
            }

        } finally {

            // 更新最后访问时间
            long now = System.currentTimeMillis();
            redisTemplate.opsForValue().set(key, String.valueOf(now));
        }
    }

    private void checkMaxAccessCount(String ip, LimitedAccess annotation) {
        if (annotation.maxAccessCount() < 0) {
            return;
        }

        final String k1 = REDIS_KEY_SCOPE + "ACCESS_COUNT:" + ip;
        final String k2 = REDIS_KEY_SCOPE + "ACCESS_COUNT_FIRST:" + ip;

        final String countString = redisTemplate.opsForValue().get(k1);

        if (countString == null) {
            long now = System.currentTimeMillis();
            redisTemplate.opsForValue().set(k1, "1", annotation.maxAccessCountTimeUnit().toMillis(1) * annotation.maxAccessCountTimeUnitNumber(), TimeUnit.MILLISECONDS);
            redisTemplate.opsForValue().set(k2, String.valueOf(now), annotation.maxAccessCountTimeUnit().toMillis(1) * annotation.maxAccessCountTimeUnitNumber(), TimeUnit.MILLISECONDS);
            return;
        }

        long count = Long.parseLong(countString);

        try {

            if (count >= annotation.maxAccessCount()) {
                throw new AccessDeniedException();
            }

        } finally {
            count += 1;
            long first = Long.parseLong(redisTemplate.opsForValue().get(k2));
            redisTemplate.opsForValue().set(k1, String.valueOf(count), annotation.maxAccessCountTimeUnit().toMillis(1) * annotation.maxAccessCountTimeUnitNumber() - (System.currentTimeMillis() - first), TimeUnit.MILLISECONDS);
        }
    }

}

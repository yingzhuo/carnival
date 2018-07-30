/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.impl;

import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaId;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单CaptchaDao实现。线程不安全，不要用在生产环境。
 *
 * @author 应卓
 */
public class UnsafeMapCaptchaDao implements CaptchaDao {

    private final Map<CaptchaId, String> cache = new HashMap<>();

    @Override
    public void save(CaptchaId id, String value) {
        cache.put(id, value);
    }

    @Override
    public String load(CaptchaId id) {
        return cache.get(id);
    }

    @Override
    public void delete(CaptchaId id) {
        cache.remove(id);
    }

}

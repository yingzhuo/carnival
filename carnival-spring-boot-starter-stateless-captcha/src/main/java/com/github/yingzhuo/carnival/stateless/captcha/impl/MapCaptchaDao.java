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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 应卓
 */
public class MapCaptchaDao implements CaptchaDao {

    private final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void save(String id, String value) {
        map.put(id, value);
    }

    @Override
    public Optional<String> load(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void delete(String id) {
        map.remove(id);
    }

}

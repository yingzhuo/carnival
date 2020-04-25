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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.5.3
 */
@Slf4j
public class MapCaptchaDao implements CaptchaDao, InitializingBean {

    private final Map<String, String> map = new HashMap<>();

    @Override
    public void save(String captchaId, String captchaValue) {
        map.put(captchaId, captchaValue);
    }

    @Override
    public Optional<String> load(String captchaId) {
        return Optional.ofNullable(map.get(captchaId));
    }

    @Override
    public void delete(String captchaId) {
        map.remove(captchaId);
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}

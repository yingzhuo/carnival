/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.dao;

import com.github.yingzhuo.carnival.patchca.CaptchaDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.2
 */
@Slf4j
public class DefaultStatelessCaptchaDao implements CaptchaDao, InitializingBean {

    private final Map<String, String> map = new HashMap<>();

    @Override
    public void save(String accessKey, String patchca) {
        map.put(accessKey, patchca);
    }

    @Override
    public String load(String accessKey) {
        return map.get(accessKey);
    }

    @Override
    public void delete(String accessKey) {
        map.remove(accessKey);
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}

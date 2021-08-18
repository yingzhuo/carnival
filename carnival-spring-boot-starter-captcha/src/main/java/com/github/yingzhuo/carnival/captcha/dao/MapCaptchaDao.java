/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.dao;

import com.github.yingzhuo.carnival.captcha.CaptchaDao;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 不可用于生产环境
 *
 * @author 应卓
 * @since 1.10.8
 */
public class MapCaptchaDao implements CaptchaDao, InitializingBean {

    private final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void save(String accessKey, String captcha) {
        map.put(accessKey, captcha);
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
        // NOP
    }

}

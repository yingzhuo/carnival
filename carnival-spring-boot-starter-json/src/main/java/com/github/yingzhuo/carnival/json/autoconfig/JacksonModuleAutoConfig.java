/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.json.module.SpringDataJacksonModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnClass(name = "org.springframework.data.domain.Page")
public class JacksonModuleAutoConfig {

    @Autowired(required = false)
    public void config(ObjectMapper om) {
        Optional.ofNullable(om).ifPresent(it -> it.registerModule(new SpringDataJacksonModule()));
    }

}

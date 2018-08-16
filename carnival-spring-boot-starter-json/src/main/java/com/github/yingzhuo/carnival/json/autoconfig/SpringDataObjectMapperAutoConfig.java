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
import com.github.yingzhuo.carnival.json.module.SpringDataModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnClass(name = "org.springframework.data.domain.Page")
public class SpringDataObjectMapperAutoConfig {

    @Autowired(required = false)
    public void config(ObjectMapper om) {
        if (om != null) {
            om.registerModule(new SpringDataModule());
        }
    }

}

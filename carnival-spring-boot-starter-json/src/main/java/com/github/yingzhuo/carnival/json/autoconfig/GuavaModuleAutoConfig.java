package com.github.yingzhuo.carnival.json.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Optional;

@ConditionalOnClass(name = "com.google.common.collect.BiMap")
public class GuavaModuleAutoConfig {

    @Autowired(required = false)
    public void config(ObjectMapper om) {
        Optional.ofNullable(om).ifPresent(it -> it.registerModule(new GuavaModule()));
    }

}

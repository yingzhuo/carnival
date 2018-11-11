/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.beanmapper.autoconfig;

import com.baidu.unbiz.easymapper.MapperFactory;
import com.baidu.unbiz.easymapper.mapping.ServiceLoaderHelper;
import com.github.yingzhuo.carnival.beanmapper.MapperRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Optional;

/**
 * @author 应卓
 */
public class BeanMapperAutoConfig implements ApplicationRunner {


    @Autowired(required = false)
    private MapperRegistry mapperRegistry;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServiceLoaderHelper.getInstance();

        Optional.ofNullable(mapperRegistry).ifPresent(registry -> registry.register(MapperFactory.getCopyByRefMapper()));
    }

}

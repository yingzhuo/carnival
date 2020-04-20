/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel;

import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 应卓
 * @since 1.5.1
 */
@Component
public class StringToResourceOptionalConverter implements Converter<String, ResourceOptional> {

    @Override
    public ResourceOptional convert(String source) {

        if (StringUtils.isBlank(source)) {
            return ResourceOptional.empty();
        }

        try {
            return ResourceOptional.of(source.split(","));
        } catch (Exception e) {
            return ResourceOptional.empty();
        }
    }

}

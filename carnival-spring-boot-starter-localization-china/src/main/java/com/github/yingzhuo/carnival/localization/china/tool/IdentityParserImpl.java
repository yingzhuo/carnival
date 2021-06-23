/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.tool;

import cn.hutool.core.util.IdcardUtil;
import com.github.yingzhuo.carnival.common.datamodel.Gender;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author 应卓
 */
public class IdentityParserImpl implements IdentityParser {

    @Override
    public Optional<IdentityDescriptor> parse(String value) {

        if (StringUtils.isBlank(value)) {
            return Optional.empty();
        }

        if (!IdcardUtil.isValidCard(value)) {
            return Optional.empty();
        }

        try {
            return Optional.of(new SimpleIdentityDescriptor(
                    value,
                    IdcardUtil.getProvinceByIdCard(value),
                    IdcardUtil.getGenderByIdCard(value) == 1 ? Gender.MALE : Gender.FEMALE,
                    IdcardUtil.getBirthDate(value),
                    IdcardUtil.getAgeByIdCard(value)
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

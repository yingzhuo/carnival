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

import com.github.yingzhuo.carnival.common.datamodel.Gender;
import com.github.yingzhuo.carnival.localization.china.jsr349.IdentityNumberConstraintValidator;
import com.github.yingzhuo.carnival.localization.china.util.IdentityUtils;
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

        if (!new IdentityNumberConstraintValidator().isValid(value, null)) {
            return Optional.empty();
        }

        try {
            return Optional.of(new SimpleIdentityDescriptor(
                    value,
                    IdentityUtils.getProvince(value),
                    IdentityUtils.isMale(value) ? Gender.MALE : Gender.FEMALE,
                    IdentityUtils.getDateOfBirth(value),
                    IdentityUtils.getAge(value)
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

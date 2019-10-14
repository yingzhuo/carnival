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
import com.github.yingzhuo.carnival.localization.china.jsr349.IdentityCardNumberConstraintValidator;
import com.github.yingzhuo.carnival.localization.china.util.IdentityCardNumberUtils;
import lombok.val;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public class IdentityCardInfoParserImpl implements IdentityCardInfoParser {

    @Override
    public Optional<IdentityCardInfo> parse(String obj, Locale locale) {

        if (!new IdentityCardNumberConstraintValidator().isValid(obj, null)) {
            return Optional.empty();
        }

        try {
            val info = new IdentityCardInfo();
            info.setAge(IdentityCardNumberUtils.getAge(obj));
            info.setDob(IdentityCardNumberUtils.getDateOfBirth(obj));
            info.setGender(IdentityCardNumberUtils.isFemale(obj) ? Gender.FEMALE : Gender.MALE);
            info.setProvince(IdentityCardNumberUtils.getProvince(obj));
            return Optional.of(info);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.jsr380;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 应卓
 */
public class IdentityNumberConstraintValidator implements ConstraintValidator<IdentityNumber, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return IdcardUtil.isValidCard(value.toString());
    }

}

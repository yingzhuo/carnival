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

import java.io.Serializable;
import java.util.Date;

/**
 * @author 应卓
 */
public interface IdentityDescriptor extends Serializable {

    /**
     * @return 省籍
     */
    public String getProvince();

    /**
     * @return 性别
     */
    public Gender getGender();

    /**
     * @return 出生日期
     */
    public Date getDob();

    /**
     * @return 当前年龄
     */
    public int getAge();

    @Override
    public String toString();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object that);

}

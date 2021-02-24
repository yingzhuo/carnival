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

import java.util.Date;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.2.2
 */
public class SimpleIdentityDescriptor implements IdentityDescriptor {

    private String value;

    private String province;

    private Gender gender;

    private Date dob;

    private int age;

    public SimpleIdentityDescriptor(String value, String province, Gender gender, Date dob, int age) {
        this.value = Objects.requireNonNull(value);
        this.province = province;
        this.gender = gender;
        this.dob = dob;
        this.age = age;
    }

    @Override
    public String getProvince() {
        return province;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Date getDob() {
        return dob;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleIdentityDescriptor that = (SimpleIdentityDescriptor) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}

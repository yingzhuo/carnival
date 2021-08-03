/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.userdetails;

import com.github.yingzhuo.carnival.common.datamodel.Gender;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.2
 */
public interface UserDetailsPlus extends UserDetails {

    public static UserDetailsPlusBuilder builder() {
        return new UserDetailsPlusBuilder();
    }

    public <T> T getId();

    public String getNickname();

    public Gender getGender();

    public <T> T getAvatar();

    public <T> T getNativeUser();

    public String getEmail();

    public String getPhoneNumber();

    public Date getDateOfBirth();

    public String getBiography();

    public Map<String, Object> getExternalData();

}

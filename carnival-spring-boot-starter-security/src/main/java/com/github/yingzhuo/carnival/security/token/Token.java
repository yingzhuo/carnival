/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token;

import com.github.yingzhuo.carnival.security.userdetails.MutableUserDetailsable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.10.2
 */
public interface Token extends
        org.springframework.security.core.token.Token,
        Authentication,
        MutableUserDetailsable,
        Serializable {

    @Override
    public String getKey();

    public void setKey(String key);

    @Override
    public Object getDetails();

    public void setDetails(Object details);

    @Override
    public long getKeyCreationTime();

    public void setKeyCreationTime(long keyCreationTime);

    @Override
    public String getExtendedInformation();

    public void setExtendedInformation(String extendedInformation);

    @Override
    public UserDetails getUserDetails();

    @Override
    public void setUserDetails(UserDetails userDetails);

}

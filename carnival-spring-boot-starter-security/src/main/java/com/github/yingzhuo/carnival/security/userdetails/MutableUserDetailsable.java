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

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 应卓
 * @see UserDetails
 * @see UserDetailsable
 * @since 1.10.6
 */
public interface MutableUserDetailsable extends UserDetailsable {

    public void setUserDetails(UserDetails userDetails);

}

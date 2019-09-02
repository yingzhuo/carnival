/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.userdetails;

import com.github.yingzhuo.carnival.common.datamodel.Gender;
import com.github.yingzhuo.carnival.restful.security.permission.Permission;
import com.github.yingzhuo.carnival.restful.security.role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * UserDetails简单实现
 *
 * @author 应卓
 * @see UserDetails
 */
@Getter
@Setter
@SuppressWarnings("unchecked")
class SimpleUserDetails implements UserDetails {

    private final Map<String, Object> payload = new HashMap<>();
    private Object id = null;
    private String username = null;
    private String email = null;
    private String password = null;
    private Date dateOfBirth = null;
    private Gender gender = null;
    private boolean expired = false;
    private boolean locked = false;
    private Collection<Role> roles = Collections.emptyList();
    private Collection<Permission> permissions = Collections.emptyList();
    private boolean admin = false;
    private Object nativeUser = null;

    public void putPayload(String name, Object value) {
        this.payload.put(name, value);
    }

}

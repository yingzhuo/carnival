/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.support;

import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.22
 */
public abstract class JpaDaoSupport {

    protected final EntityManager em;

    protected JpaDaoSupport(EntityManager em) {
        this.em = Objects.requireNonNull(em);
    }

}

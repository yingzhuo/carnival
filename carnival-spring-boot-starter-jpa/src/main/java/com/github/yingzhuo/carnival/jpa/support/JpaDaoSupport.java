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

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.10.22
 */
public abstract class JpaDaoSupport {

    protected final EntityManager em;

    protected JpaDaoSupport(EntityManager em) {
        this.em = Objects.requireNonNull(em);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final boolean isNotNull(Object obj) {
        return obj != null;
    }

    protected final <T> boolean isNotEmpty(T[] xs) {
        return isNotNull(xs) && xs.length != 0;
    }

    protected final <T> boolean isNotEmpty(Collection<T> xs) {
        return !CollectionUtils.isEmpty(xs);
    }

    protected final <K, V> boolean isNotEmpty(Map<K, V> xs) {
        return !CollectionUtils.isEmpty(xs);
    }

    protected final boolean isNotEmpty(String s) {
        return StringUtils.hasLength(s);
    }

    protected final boolean isNotBlank(String s) {
        return StringUtils.hasText(s);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final <T> Set<T> toSet(T[] xs) {
        if (!isNotNull(xs)) {
            return new HashSet<>();
        }
        return Arrays.stream(xs).collect(Collectors.toSet());
    }

    protected final <T> Set<T> toSet(Collection<T> xs) {
        if (!isNotNull(xs)) {
            return new HashSet<>();
        }
        if (xs instanceof Set) {
            return (Set<T>) xs;
        }
        return new HashSet<>(xs);
    }

}

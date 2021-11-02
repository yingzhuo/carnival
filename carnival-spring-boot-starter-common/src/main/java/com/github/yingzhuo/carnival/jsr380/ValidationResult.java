/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr380;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.10.34
 */
public final class ValidationResult<T> implements Serializable {

    private final Set<ConstraintViolation<T>> violationSet;

    public ValidationResult(Set<ConstraintViolation<T>> violationSet) {
        this.violationSet = violationSet != null ? Collections.unmodifiableSet(violationSet) : Collections.emptySet();
    }

    public boolean hasError() {
        return !hasNotError();
    }

    public boolean hasNotError() {
        return violationSet.isEmpty();
    }

    public Set<String> getMessages() {
        if (hasError()) {
            return violationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    public Optional<String> getJoinedMessage() {
        return getJoinedMessage(",");
    }

    public Optional<String> getJoinedMessage(String delimiter) {
        return Optional.ofNullable(String.join(delimiter, getMessages()));
    }

    public int getErrorCount() {
        return violationSet.size();
    }

}

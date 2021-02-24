/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author 应卓
 * @since 1.6.26
 */
class SimplePageImpl<T> implements Page<T> {

    private final Page<T> delegate;

    SimplePageImpl(@JsonProperty("content") List<T> content,
                   @JsonProperty("number") int number,
                   @JsonProperty("size") int size,
                   @JsonProperty("totalElements") long totalElements) {
        final PageRequest pageRequest = PageRequest.of(number, size);
        delegate = new PageImpl<>(content, pageRequest, totalElements);
    }

    @Override
    @JsonProperty
    public int getTotalPages() {
        return delegate.getTotalPages();
    }

    @Override
    @JsonProperty
    public long getTotalElements() {
        return delegate.getTotalElements();
    }

    @Override
    @JsonProperty
    public int getNumber() {
        return delegate.getNumber();
    }

    @Override
    @JsonProperty
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    @JsonProperty
    public int getNumberOfElements() {
        return delegate.getNumberOfElements();
    }

    @Override
    @JsonProperty
    public List<T> getContent() {
        return delegate.getContent();
    }

    @Override
    @JsonProperty
    public boolean hasContent() {
        return delegate.hasContent();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return delegate.getSort();
    }

    @Override
    @JsonProperty
    public boolean isFirst() {
        return delegate.isFirst();
    }

    @Override
    @JsonProperty
    public boolean isLast() {
        return delegate.isLast();
    }

    @Override
    @JsonIgnore
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    @JsonIgnore
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    @JsonIgnore
    public Pageable nextPageable() {
        return delegate.nextPageable();
    }

    @Override
    @JsonIgnore
    public Pageable previousPageable() {
        return delegate.previousPageable();
    }

    @Override
    @JsonIgnore
    public <S> Page<S> map(Function<? super T, ? extends S> converter) {
        return delegate.map(converter);
    }

    @Override
    @JsonIgnore
    public Iterator<T> iterator() {
        return delegate.iterator();
    }
}

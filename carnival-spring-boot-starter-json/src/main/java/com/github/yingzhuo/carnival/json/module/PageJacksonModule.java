/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author 应卓
 * @since 1.6.17
 */
public class PageJacksonModule extends Module {

    public PageJacksonModule() {
    }

    @Override
    public String getModuleName() {
        return "PageJacksonModule";
    }

    @Override
    public Version version() {
        return new Version(0, 1, 0, "", null, null);
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Page.class, PageMixIn.class);
    }

    @JsonDeserialize(as = SimplePageImpl.class)
    private interface PageMixIn {
    }

    static class SimplePageImpl<T> implements Page<T> {

        private final Page<T> delegate;

        SimplePageImpl(@JsonProperty("content") List<T> content,
                       @JsonProperty("number") int number, @JsonProperty("size") int size,
                       @JsonProperty("totalElements") long totalElements,
                       @JsonProperty("sort") Sort sort) {
            final PageRequest pageRequest;
            if (sort != null) {
                pageRequest = PageRequest.of(number, size, sort);
            } else {
                pageRequest = PageRequest.of(number, size);
            }
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

}

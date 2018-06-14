/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
@Getter
@Setter
public class Page<T> implements Iterable<T>, Serializable {

    public static <T> Page<T> empty() {
        return empty(1, 0);
    }

    public static <T> Page<T> empty(int number, int size) {
        Page<T> page = new Page<>();
        page.number = number;
        page.size = size;
        page.totalElements = 0;
        page.totalPages = 0;
        page.hasPrevious = false;
        page.hasNext = false;
        return page;
    }

    public static <T> Page<T> create(List<T> content, int totalElements, int number, int size) {
        Page<T> page = new Page<>();
        if (content == null) {
            content = Collections.emptyList();
        }

        page.content = content;
        page.number = number;
        page.size = size;
        page.totalElements = totalElements;
        page.totalPages = (totalElements / size) + (totalElements % size == 0 ? 0 : 1);
        page.hasNext = (number < page.totalPages);
        page.hasPrevious = number > 1;
        return page;
    }

    private Page() {
        super();
    }

    private List<T> content = Collections.emptyList();

    private int number;

    private int size;

    private int totalElements;

    private int totalPages;

    private boolean hasPrevious;

    private boolean hasNext;

    @Override
    public Iterator<T> iterator() {
        return content != null ? content.iterator() : Collections.emptyIterator();
    }

    public <U> Page<U> map(Function<? super T, ? extends U> function) {
        Page<U> page = new Page<>();
        page.content = this.content.stream().map(function).collect(Collectors.toList());
        page.number = this.number;
        page.size = this.size;
        page.totalElements = this.totalElements;
        page.totalPages = this.totalPages;
        page.hasNext = this.hasNext;
        page.hasPrevious = this.hasPrevious;
        return page;
    }
}

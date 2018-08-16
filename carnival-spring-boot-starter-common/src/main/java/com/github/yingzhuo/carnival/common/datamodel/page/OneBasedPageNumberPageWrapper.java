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

import org.springframework.data.domain.Page;

import java.util.Objects;

/**
 * 如果你的项目中页码总是从1开始，你需要这个
 *
 * @author 应卓
 */
public class OneBasedPageNumberPageWrapper<T> extends PageWrapper<T> {

    public static <T> Page<T> of(Page<T> page) {
        return new OneBasedPageNumberPageWrapper<>(Objects.requireNonNull(page));
    }

    public OneBasedPageNumberPageWrapper(Page<T> delegate) {
        super(delegate);
    }

    @Override
    public int getNumber() {
        return super.getNumber() + 1;
    }

}

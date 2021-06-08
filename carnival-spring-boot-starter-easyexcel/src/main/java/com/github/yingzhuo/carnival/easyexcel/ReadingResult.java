/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel;

import java.io.Serializable;
import java.util.List;

/**
 * @param <M> 数据模型
 * @author 应卓
 * @since 1.9.2
 */
public interface ReadingResult<M> extends Serializable {

    public List<M> getModels();

    public default int getModelSize() {
        List<M> models = getModels();
        return models != null ? models.size() : 0;
    }

    public List<ReadingError> getErrors();

    public default int getErrorSize() {
        List<ReadingError> errors = getErrors();
        return errors != null ? errors.size() : 0;
    }

}

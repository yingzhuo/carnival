/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.Connection;

import java.io.Serializable;

/**
 * 命令抽象
 *
 * @author tobato
 */
public interface Command<T> extends Serializable {

    public T execute(Connection conn);

}

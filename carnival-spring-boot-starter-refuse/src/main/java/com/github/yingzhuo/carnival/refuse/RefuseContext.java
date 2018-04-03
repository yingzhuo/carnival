/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author 应卓
 * @see RefusingListener
 */
@Data
@AllArgsConstructor
public final class RefuseContext {

    private Date timestamp;
    private String requestURI;
    private String reason;

}

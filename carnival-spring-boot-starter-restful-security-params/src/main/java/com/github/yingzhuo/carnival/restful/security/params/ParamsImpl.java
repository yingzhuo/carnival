/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params;

import lombok.*;

/**
 * @author 应卓
 * @since 1.6.30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParamsImpl implements MutableParams {
    private String nonce;
    private Long timestamp;
    private String sign;
}

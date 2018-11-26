/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.wechatpay;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayKey {

    private String appid = "";//微信 应用ID
    private String partnerid = "";//微信 商户号
    private String prepayid = "";//微信 预支付交易会话ID
    private String packageValue = "";//微信 扩展字段
    private String noncestr = "";//微信 随机字符串
    private long timestamp = 0;//微信 时间戳
    private String sign = "";//微信 签名
    private String orderNo = "";//订单号

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.alipay;

/**
 * @author 应卓
 */
public interface AlipayService {

    public String createPaymentKey(String tradeNo, int amountOfMoney, String subject, String expireTime, String passbackParams);

    public boolean isTradeSuccess(String tradeNo);

}

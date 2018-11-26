/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.wechatpay.impl;

import com.github.yingzhuo.carnival.wechatpay.PayKey;
import com.github.yingzhuo.carnival.wechatpay.WechatPayService;
import com.github.yingzhuo.carnival.wechatpay.util.HttpUtils;
import com.github.yingzhuo.carnival.wechatpay.util.SignatureHelper;
import com.github.yingzhuo.carnival.wechatpay.util.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
@Slf4j
public class DefaultWechatPayService implements WechatPayService {

    private static final String WECHAT_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String SIGN_TYPE = "MD5";
    private static final String TRAD_TYPE = "APP";

    private String appId;
    private String mchId;
    private String secretKey;
    private String notifyUrl;
    private boolean debugMode = true;

    @Override
    public PayKey createTradeKey(String tradeNo, int amountOfMoney, String subject, String timeExpire, String ipAddress) {

        try {
            String nonce_str = RandomStringUtils.randomAlphanumeric(32);
            String total_fee = converterAmountOfMoney(amountOfMoney);

            Map<String, String> params = new HashMap<>();
            params.put("appid", appId);
            params.put("mch_id", mchId);
            params.put("nonce_str", nonce_str);
            params.put("sign_type", SIGN_TYPE);
            params.put("body", subject);
            params.put("out_trade_no", tradeNo);
            params.put("total_fee", total_fee);
            params.put("spbill_create_ip", ipAddress);
            if (timeExpire != null) {
                params.put("time_expire", timeExpire);//prepay_id只有两小时的有效期
            }
            params.put("notify_url", notifyUrl);
            params.put("trade_type", TRAD_TYPE);
//        params.put("attach", attach);
            String sign = SignatureHelper.getInstance().getSign2(params, "key", secretKey, false, SignatureHelper.DigestType.MD5, SignatureHelper.CharacterCase.UPPER_CASE);
            params.put("sign", sign);

            String wxReqStr = WXPayUtil.mapToXml(params);
            String resultInfo = HttpUtils.syncXmlPost(WECHAT_URL, wxReqStr);
            Map<String, String> resultObject = WXPayUtil.xmlToMap(resultInfo);

            if (StringUtils.equalsIgnoreCase("SUCCESS", resultObject.get("return_code"))) {
                PayKey payKey = new PayKey();
                payKey.setAppid(this.appId);
                payKey.setPartnerid(this.mchId);
                payKey.setPrepayid(resultObject.get("prepay_id"));
                payKey.setPackageValue("Sign=WXPay");
                payKey.setNoncestr(RandomStringUtils.randomAlphanumeric(32));
                payKey.setTimestamp(System.currentTimeMillis() / 1000);
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("appid", payKey.getAppid());
                hashMap.put("partnerid", payKey.getPartnerid());
                hashMap.put("prepayid", payKey.getPrepayid());
                hashMap.put("noncestr", payKey.getNoncestr());
                hashMap.put("timestamp", Long.toString(payKey.getTimestamp()));
                hashMap.put("package", payKey.getPackageValue());
                String paySign = SignatureHelper.getInstance().getSign2(hashMap, "key", this.secretKey, false, SignatureHelper.DigestType.MD5, SignatureHelper.CharacterCase.UPPER_CASE);
                payKey.setSign(paySign);
                return payKey;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public boolean isTradeSuccess(String tradeNo) {
        if (this.debugMode) {
            return true;
        }

        try {
            final String nonce = RandomStringUtils.randomAlphanumeric(32);
            Map<String, String> params = new HashMap<>();
            params.put("appid", this.appId);
            params.put("mch_id", this.mchId);
            params.put("nonce_str", nonce);
            params.put("out_trade_no", tradeNo);//微信的订单号，优先使用 transaction_id  二选一
            String sign = SignatureHelper.getInstance().getSign2(params, "key", this.secretKey, false, SignatureHelper.DigestType.MD5, SignatureHelper.CharacterCase.UPPER_CASE);
            params.put("sign", sign);
            String wxReqStr = WXPayUtil.mapToXml(params);
            log.info("wxReqStr:{}", wxReqStr);
            String resultInfo = HttpUtils.syncXmlPost(WECHAT_URL, wxReqStr);
            log.info("resultInfo:{}", resultInfo);
            Map<String, String> resultObject = WXPayUtil.xmlToMap(resultInfo);
            if (StringUtils.equalsIgnoreCase("SUCCESS", resultObject.get("return_code"))) {
                if (StringUtils.equalsIgnoreCase("SUCCESS", resultObject.get("result_code"))) {
                    String trade_state = resultObject.get("trade_state");
                    log.info("交易状态:{}", trade_state);
                    if (StringUtils.equalsIgnoreCase("SUCCESS", trade_state)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return false;
    }

    private String converterAmountOfMoney(int amountOfMoney) {
        if (debugMode) {
            return "1";
        }

        return Integer.toString(amountOfMoney);
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

}

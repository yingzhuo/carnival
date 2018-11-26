/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.alipay.mvc;

import com.github.yingzhuo.carnival.alipay.AlipayNotifyCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 */
@Slf4j
public class AlipayNotifyFilter extends OncePerRequestFilter {

    private AlipayNotifyCallback alipayNotifyCallback;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

//        @RequestParam(value = "notify_time", required = false) String notifyTime,//1 通知的发送时间
//        @RequestParam(value = "notify_type", required = false) String notifyType,//1 通知的类型 trade_status_sync
//        @RequestParam(value = "notify_id", required = false) String notifyId,//1 通知校验ID
//        @RequestParam(value = "app_id", required = false) String appId,//1 支付宝分配给开发者的应用Id
//        @RequestParam(value = "charset", required = false) String charset,//1 编码格式，如utf-8、gbk、gb2312等
//        @RequestParam(value = "version", required = false) String version,//1 接口版本
//        @RequestParam(value = "sign_type", required = false) String signType,//1 签名类型:RSA2
//        @RequestParam(value = "sign", required = false) String sign,//1 签名
//        @RequestParam(value = "trade_no", required = false) String tradeNo, //1 支付宝交易凭证号
//        @RequestParam(value = "out_trade_no", required = false) String outTradeNo, //1 商户订单号 交易单号
//        @RequestParam(value = "out_biz_no", required = false) String outBizNo, //0 商户业务号
//        @RequestParam(value = "buyer_id", required = false) String buyerId, //0 买家支付宝用户号 以2088开头的纯16位数字
//        @RequestParam(value = "buyer_logon_id", required = false) String buyerLogonId, //0 买家支付宝账号
//        @RequestParam(value = "seller_id", required = false) String sellerId,//0 卖家支付宝用户号
//        @RequestParam(value = "seller_email", required = false) String sellerEmail,//0 卖家支付宝账号
//        @RequestParam(value = "trade_status", required = false) String tradeStatus,//0 交易目前所处的状态，
//        @RequestParam(value = "total_amount", required = false) String totalAmount,//0 本次交易支付的订单金额，单位为人民币（元）
//        @RequestParam(value = "receipt_amount", required = false) String receiptAmount,//0 商家在交易中实际收到的款项，单位为元
//        @RequestParam(value = "invoice_amount", required = false) String invoiceAmount,//0 用户在交易中支付的可开发票的金额
//        @RequestParam(value = "buyer_pay_amount", required = false) String buyerPayAmount,//0 用户在交易中支付的金额
//        @RequestParam(value = "point_amount", required = false) String pointAmount,//0 使用集分宝支付的金额
//        @RequestParam(value = "refund_fee", required = false) String refundFee,//0 总退款金额//0 优惠券信息
//        @RequestParam(value = "subject", required = false) String subject,//0 订单标题
//        @RequestParam(value = "body", required = false) String body,//0 商品描述
//        @RequestParam(value = "gmt_create", required = false) String gmtCreate, //0 交易创建时间
//        @RequestParam(value = "gmt_payment", required = false) String gmtPayment, //0 付款时间
//        @RequestParam(value = "gmt_refund", required = false) String gmtRefund, //0 交易退款时间
//        @RequestParam(value = "gmt_close", required = false) String gmtClose, //0 交易结束时间
//        @RequestParam(value = "fund_bill_list", required = false) String fundBillList, //0 支付金额信息
//        @RequestParam(value = "passback_params", required = false) String passbackParams, //0 公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。
//        @RequestParam(value = "voucher_detail_list", required = false) String voucherDetailList

        try {
            alipayNotifyCallback.execute(new AlipayNotifyCallback.CallbackParameters(request));
            response.getWriter().write("success");
            response.getWriter().flush();
        } catch (Exception e) {
            try {
                response.getWriter().write("failure");
                response.getWriter().flush();
            } catch (IOException e1) {
                throw new UncheckedIOException(e1);
            }
        }

    }

    public AlipayNotifyCallback getAlipayNotifyCallback() {
        return alipayNotifyCallback;
    }

    public void setAlipayNotifyCallback(AlipayNotifyCallback alipayNotifyCallback) {
        this.alipayNotifyCallback = alipayNotifyCallback;
    }

}

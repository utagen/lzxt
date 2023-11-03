package com.mszlu.xt.web.service;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.OrderParam;

public interface OrderService {
    /**
     * 提交订单
     * @param orderParam 订单param
     * @return
     */
    CallResult submitOrder(OrderParam orderParam);

    /**
     * 根据订单id和支付类型生成支付二维码
     * @param orderParam
     * @return
     */
    CallResult wxPay(OrderParam orderParam);

    /**
     * 微信支付回调 在此接口处理订单支付成功的相关操作
     * @param xmlData
     * @return
     */
    CallResult notifyOrder(String xmlData);

    /**
     * 根据订单id查询订单详情
     * @param orderParam
     * @return
     */
    CallResult findOrder(OrderParam orderParam);

    /**
     * 订单列表
     * @param orderParam
     * @return
     */
    CallResult orderList(OrderParam orderParam);
}

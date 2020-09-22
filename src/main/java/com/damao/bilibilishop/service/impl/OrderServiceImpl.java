package com.damao.bilibilishop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.dao.OrderDao;
import com.damao.bilibilishop.dao.OrderItemDao;
import com.damao.bilibilishop.dao.OrderShippingDao;
import com.damao.bilibilishop.module.Order;
import com.damao.bilibilishop.module.OrderItem;
import com.damao.bilibilishop.module.OrderShipping;
import com.damao.bilibilishop.module.dto.param.OrderItemParam;
import com.damao.bilibilishop.module.dto.param.OrderParam;
import com.damao.bilibilishop.service.OrderService;
import com.damao.bilibilishop.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 呆毛
 */
@Service
public class OrderServiceImpl implements OrderService {
    public static final String ORDER_ID_GEN_KEY = "gen_key:order_id";
    public static final String ORDER_ITEM_ID_GEN_KEY = "gen_key:order_item_id";
    public static final String ORDER_DEFAULT_KEY = "1000000";

    @Autowired
    RedisService redisService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    OrderShippingDao orderShippingDao;
    /**
     * 1、利用redis的incr自增方法生成订单号（order_id） 使用incr可读星号，不会重复
     * 2、如果redis中不存在key，我们则创建一个key，并给一个默认值
     * 3、将传来的参数分别插入三张表中
     * @param orderParam 获取用户传来的参数
     * @return
     */
    @Override
    public CommonResult<String> createOrder(OrderParam orderParam){
        //redis中不存在订单注册机
        if (StrUtil.isBlank(redisService.get(ORDER_ID_GEN_KEY))){
            //默认生成订单注册机
            redisService.set(ORDER_ID_GEN_KEY,ORDER_DEFAULT_KEY);
        }
        if (StrUtil.isBlank(redisService.get(ORDER_ITEM_ID_GEN_KEY))){
            //默认生成订单注册机
            redisService.set(ORDER_ID_GEN_KEY,ORDER_ITEM_ID_GEN_KEY);
        }
        //获取当前时间
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //如果存在此key则采用incr自增
        String orderId = redisService.incr(ORDER_ID_GEN_KEY,null).toString();
        //插入订单表 t_order
        Order order = new Order()
                .setOrderId(orderId)
                //'状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
                .setStatus(1)
                .setCreateTime(timestamp)
                .setUpdateTime(timestamp)
                .setPayment(orderParam.getPayment())
                .setPostFee(orderParam.getPostFee())
                .setUserId(orderParam.getUserId())
                .setBuyerMessage(orderParam.getBuyerMessage())
                .setBuyerNick(orderParam.getBuyerNick());
        orderDao.insert(order);
        //插入详情数据表 t_order_item
        for (OrderItemParam orderItemParam:orderParam.getOrderItems()){
            String itemId = redisService.incr(ORDER_ITEM_ID_GEN_KEY, null).toString();
            OrderItem orderItem = new OrderItem()
                    .setItemId(itemId)
                    .setOrderId(orderId)
                    .setNum(orderItemParam.getNum())
                    .setPrice(new BigDecimal(orderItemParam.getPrice().toString()))
                    .setTotalFee(new BigDecimal(orderItemParam.getTotalFee().toString()))
                    .setTitle(orderItemParam.getTitle())
                    .setPicPath(orderItemParam.getPicPath());
            orderItemDao.insert(orderItem);
        }
        //插入订单物流表
        OrderShipping orderShipping = new OrderShipping()
                .setOrderId(orderId)
                .setCreatedTime(timestamp)
                .setUpdateTime(timestamp)
                .setReceiverAddress(orderParam.getOrderShippingParam().getReceiverAddress())
                .setReceiverCity(orderParam.getOrderShippingParam().getReceiverCity())
                .setReceiverDistrict(orderParam.getOrderShippingParam().getReceiverDistrict())
                .setReceiverMobile(orderParam.getOrderShippingParam().getReceiverMobile())
                .setReceiverName(orderParam.getOrderShippingParam().getReceiverName())
                .setReceiverPhone(orderParam.getOrderShippingParam().getReceiverPhone())
                .setReceiverState(orderParam.getOrderShippingParam().getReceiverState())
                .setReceiverZip(orderParam.getOrderShippingParam().getReceiverZip());
        orderShippingDao.insert(orderShipping);
        return CommonResult.success(orderId,"订单创建成功");
    }
}

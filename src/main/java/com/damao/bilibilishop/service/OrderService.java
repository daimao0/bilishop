package com.damao.bilibilishop.service;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.dto.OrderDto;
import com.damao.bilibilishop.module.dto.param.OrderParam;

import java.util.List;

/**
 * 订单服务
 * @author 呆毛
 */

public interface OrderService {
    /**
     * 创建用户订单
     * @param orderParam 获取用户传来的参数
     * @return
     */
    CommonResult<String> createOrder(OrderParam orderParam);

    /**
     * 通过用户id查询订单
     * @param userId 用户id
     * @return 订单
     */
    CommonResult<List<OrderDto>>listOrdersByUserId(Long userId);
}

package com.damao.bilibilishop.controller;

import cn.hutool.core.util.StrUtil;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.dto.OrderDto;
import com.damao.bilibilishop.module.dto.param.OrderParam;
import com.damao.bilibilishop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单接口
 * @author 呆毛
 */
@RestController
@Api(tags = "OrderController",description = "订单接口")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "查看用户订单")
    @GetMapping("/listOrdersByUserId")
    public CommonResult<List<OrderDto>> listOrdersByUserId(Long id, String loginFlag){
        if (StrUtil.isBlank(loginFlag)){
            return CommonResult.forbidden("请先登录");
        }
        return orderService.listOrdersByUserId(id);
    }

    @ApiOperation(value = "创建用户订单")
    @PostMapping("/createOrder")
    public CommonResult<String> createOrder(@ApiParam("传入相关参数") @RequestBody  OrderParam orderParam){

        return orderService.createOrder(orderParam);
    }
}

package com.damao.bilibilishop.module.dto.param;

import com.damao.bilibilishop.module.OrderShipping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 呆毛
 */
@ApiModel(value = "订单信息参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderParam implements Serializable {
    @ApiModelProperty("订单金额")
    private String payment;

    @ApiModelProperty("邮费")
    private String postFee;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("买家留言")
    private String buyerMessage;

    @ApiModelProperty("买家昵称")
    private String buyerNick;

    @ApiModelProperty("订单详情表")
    private List<OrderItemParam> orderItems;

    @ApiModelProperty("订单配送表")
    private OrderShippingParam orderShippingParam;

}

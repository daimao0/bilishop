package com.damao.bilibilishop.module.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单详情表
 * @author 呆毛
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("订单详情表参数")
public class OrderItemParam implements Serializable {


    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty("商品标题")
    private String title;

    @ApiModelProperty("商品单价")
    private Double price;

    @ApiModelProperty("商品总价")
    private Double totalFee;

    @ApiModelProperty("商品图片")
    private String picPath;


}
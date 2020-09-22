package com.damao.bilibilishop.module.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单配送表
 *
 * @author 呆毛
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单物流表参数")
public class OrderShippingParam implements Serializable {

    @ApiModelProperty("收货人全名")
    private String receiverName;

    @ApiModelProperty("收货人座机")
    private String receiverPhone;

    @ApiModelProperty("收货人手机")
    private String receiverMobile;

    @ApiModelProperty("省")
    private String receiverState;

    @ApiModelProperty("市")
    private String receiverCity;

    @ApiModelProperty("区")
    private String receiverDistrict;

    @ApiModelProperty("收货人详细地址")
    private String receiverAddress;

    @ApiModelProperty("邮政编码")
    private String receiverZip;

}

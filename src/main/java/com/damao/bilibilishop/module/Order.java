package com.damao.bilibilishop.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单表
 * @author 呆毛
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {
    @TableId("order_id")
    private String orderId;

    @TableField("payment")
    private String payment;

    @TableField("payment_type")
    private Integer paymentType;

    @TableField("post_fee")
    private String postFee;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

    @TableField("payment_time")
    private Timestamp paymentTime;

    @TableField("consign_time")
    private Timestamp consignTime;

    @TableField("end_time")
    private Timestamp endTime;

    @TableField("close_time")
    private Timestamp closeTime;

    @TableField("shipping_name")
    private String shippingName;

    @TableField("shipping_code")
    private String shippingCode;

    @TableField("user_id")
    private Long userId;

    @TableField("buyer_message")
    private String buyerMessage;

    @TableField("buyer_nick")
    private String buyerNick;

    @TableField("buyer_rate")
    private Integer buyerRate;

}

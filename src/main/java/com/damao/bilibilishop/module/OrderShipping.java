package com.damao.bilibilishop.module;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 订单配送表
 *
 * @author 呆毛
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order_shipping")
public class OrderShipping implements Serializable {

    @TableId(value = "order_id",type = IdType.INPUT)
    private String orderId;

    @TableField("receiver_name")
    private String receiverName;

    @TableField("receiver_phone")
    private String receiverPhone;

    @TableField("receiver_mobile")
    private String receiverMobile;

    @TableField("receiver_state")
    private String receiverState;

    @TableField("receiver_city")
    private String receiverCity;

    @TableField("receiver_district")
    private String receiverDistrict;

    @TableField("receiver_address")
    private String receiverAddress;

    @TableField("receiver_zip")
    private String receiverZip;

    @TableField("created_time")
    private Timestamp createdTime;

    @TableField("update_time")
    private Timestamp updateTime;

}

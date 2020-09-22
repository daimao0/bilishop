package com.damao.bilibilishop.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情表
 * @author 呆毛
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_order_item")
public class OrderItem implements Serializable {


    @TableId("item_id")
    private String itemId;

    @TableField("order_id")
    private String orderId;

    @TableField("num")
    private Integer num;

    @TableField("title")
    private String title;

    @TableField("price")
    private BigDecimal price;

    @TableField("total_fee")
    private BigDecimal totalFee;

    @TableField("pic_path")
    private String picPath;
}

package com.damao.bilibilishop.module.dto;

import com.damao.bilibilishop.module.Order;
import com.damao.bilibilishop.module.OrderItem;
import com.damao.bilibilishop.module.OrderShipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 呆毛
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDto {

    private Order order;

    private List<OrderItem> orderItems;

    private OrderShipping orderShipping;
}

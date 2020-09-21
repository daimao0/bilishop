package com.damao.bilibilishop.module.dto;

import com.damao.bilibilishop.module.Ticket;
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
@ApiModel(value = "门票传输信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TicketDto implements Serializable {
    @ApiModelProperty(value = "页数")
    private Integer pageNums;
    @ApiModelProperty(value = "总门票数")
    private Integer total;
    @ApiModelProperty(value = "一个页面有多少条记录")
    private Integer pageSize;
    @ApiModelProperty(value = "该页的结果集")
    private List<Ticket> result;
}

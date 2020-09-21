package com.damao.bilibilishop.controller;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 呆毛
 */
@RestController
@Api(tags = "TicketsController", description = "获取商品门票")
@RequestMapping("/ticket/list")
public class TicketsController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/{page}")
    @ApiOperation(value = "通过改变page的数字来改变页数")
    public CommonResult<TicketDto> listTicketsByPage(@PathVariable @ApiParam(value = "传入页码") Integer page) {
        return ticketService.queryTicketForPage(page, 16);

    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索栏搜索相关数据")
    public CommonResult<TicketDto> listTicketsBySearch(@ApiParam(value = "传入查询内容，查询页码，查询页面大小") String searchContext,Integer page,Integer pageSize) {
        return ticketService.listTicketsBySearch(searchContext,page,pageSize);
    }
}

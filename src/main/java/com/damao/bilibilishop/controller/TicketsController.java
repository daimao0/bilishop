package com.damao.bilibilishop.controller;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.TicketService;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "通过页码获取门票信息")
    public CommonResult<TicketDto> listTicketsByPage(@PathVariable @ApiParam(name = "page",value = "页码",type = "path") Integer page) {
        return ticketService.queryTicketForPage(page, 16);

    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索栏搜索相关数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchContext",value = "查询内容",dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "page",value = "页码",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",paramType = "query")
    })
    public CommonResult<TicketDto> listTicketsBySearch(String searchContext,
                                                       Integer page,
                                                       Integer pageSize) {
        return ticketService.listTicketsBySearch(searchContext,page,pageSize);
    }

}

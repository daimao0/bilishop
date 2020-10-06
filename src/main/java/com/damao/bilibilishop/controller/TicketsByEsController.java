package com.damao.bilibilishop.controller;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.common.crawler.BiLiBiLiShopTicket;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 从es中查询数据
 * @author 呆毛
 */
@RestController
@Api(tags = "TicketsByEsController",description = "通过ElasticSearch查询门票")
@RequestMapping("/es/order")
public class TicketsByEsController {
    @Autowired
    private BiLiBiLiShopTicket biLiBiLiShopTicket;

    @Autowired
    TicketService ticketService;

    @GetMapping("/insertES")
    @ApiOperation(value = "讲哔哩哔哩会员购的数据存储到ES中")
    public CommonResult<Boolean> insertTickets() throws IOException {
        return biLiBiLiShopTicket.insertTickets();
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
                                                       Integer pageSize) throws IOException {
        return ticketService.searchTicketsByEs(searchContext,page,pageSize);
    }
}

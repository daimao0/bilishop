package com.damao.bilibilishop.controller;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呆毛
 */
@RestController
@Api(tags = "TicketsController",description = "获取商品门票")
public class TicketsController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/ticket/list/{page}")
    @ApiOperation(value = "通过改变page的数字来改变页数")
    public CommonResult<TicketDto> listTicketsByPage(@PathVariable Integer page) {
        return ticketService.queryTicketForPage(page, 16);

    }
}

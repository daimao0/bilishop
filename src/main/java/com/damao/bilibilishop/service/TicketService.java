package com.damao.bilibilishop.service;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.Ticket;
import com.damao.bilibilishop.module.dto.TicketDto;

/**
 * @author 呆毛
 */
public interface TicketService {
    /**
     * 插入门票
     * @param ticket 传入一个门票
     */
    void insertTicket(Ticket ticket);

    /**
     * 分页查询
     * @param page 当前页
     * @param pageSize 页面大小
     */
    CommonResult<TicketDto> queryTicketForPage(Integer page, Integer pageSize);
}

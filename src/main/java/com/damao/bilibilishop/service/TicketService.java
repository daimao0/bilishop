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
     * @return 返回结果
     */
    CommonResult<TicketDto> queryTicketForPage(Integer page, Integer pageSize);

    /**
     *  通过搜索栏中的数据查询结果
     * @param searchContext 查询内容
     * @param page  第几页
     * @param pageSize 页面大小
     * @return 返回结果
     */
    CommonResult<TicketDto> listTicketsBySearch(String searchContext,Integer page,Integer pageSize);
}

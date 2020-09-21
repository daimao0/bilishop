package com.damao.bilibilishop.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.dao.TicketDao;
import com.damao.bilibilishop.module.Ticket;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.RedisService;
import com.damao.bilibilishop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 呆毛
 */
@Service
public class TicketServiceImpl implements TicketService {

    public static final String JSON_TICKET_PAGE="json:tickets_page:";

    @Autowired
    TicketDao ticketDao;

    @Autowired
    RedisService redisService;

    @Override
    public void insertTicket(Ticket ticket) {
        Integer code = ticketDao.insert(ticket);
        if (code==1){
            System.out.println(ticket.toString()+"插入成功");
        }else{
            System.out.println("插入失败，请检查字段是否匹配");
        }
    }

    @Override
    public CommonResult<TicketDto> queryTicketForPage(Integer page, Integer pageSize) {
        //查询缓存中是否存在该页的信息
        if (redisService.get(JSON_TICKET_PAGE+page)==null){
            //参数一是当前页，参数二是每页大小
            IPage<Ticket> ticketPage = new Page<>(page,pageSize);
            ticketPage = ticketDao.selectPage(ticketPage, new QueryWrapper<Ticket>().orderBy(true,true,"rank_offset"));
            List<Ticket> tickets = ticketPage.getRecords();
            String ticketJson = JSON.toJSONString(tickets);
            redisService.set(JSON_TICKET_PAGE+page,ticketJson);
            //查询数据库中所有数据的条数
            Integer total = ticketDao.selectCount(null);
            TicketDto ticketDto = new TicketDto().setResult(tickets).setTotal(total).setPageNums((total / pageSize)+1).setPageSize(pageSize);
            return CommonResult.success(ticketDto);
        }
        String ticketsJson = redisService.get(JSON_TICKET_PAGE + page);
        List<Ticket> tickets = JSON.parseArray(ticketsJson, Ticket.class);
        //查询数据库中所有数据的条数
        Integer total = ticketDao.selectCount(null);
        TicketDto ticketDto = new TicketDto().setResult(tickets).setTotal(total).setPageNums((total / pageSize)+1).setPageSize(pageSize);
        return CommonResult.success(ticketDto);
    }


}


package com.damao.bilibilishop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.bilibilishop.module.Ticket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 呆毛
 */
@Mapper
public interface TicketDao extends BaseMapper<Ticket> {
    /**
     * 通过搜索栏查询返回结果
     * @param searchContext 搜索内容
     * @return 返回搜索结果
     */

    List<Ticket> listTicketsBySearch(String searchContext,int page,int pageSize);

    /**
     * 查询搜索结果总记录
     * @param searchContext 搜索内容
     * @return 总记录数
     */
    Integer getSearchCount(String searchContext);
}

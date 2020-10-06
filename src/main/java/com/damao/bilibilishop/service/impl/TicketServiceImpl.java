package com.damao.bilibilishop.service.impl;


import cn.hutool.Hutool;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.dao.TicketDao;
import com.damao.bilibilishop.module.Ticket;
import com.damao.bilibilishop.module.dto.TicketDto;
import com.damao.bilibilishop.service.RedisService;
import com.damao.bilibilishop.service.TicketService;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 呆毛
 */
@Service
public class TicketServiceImpl implements TicketService {

    public static final String JSON_TICKETS_PAGE = "json:tickets_page:";
    public static final String JSON_TICKETS_SEARCH = "json:tickets_city:";
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void insertTicket(Ticket ticket) {
        Integer code = ticketDao.insert(ticket);
        if (code == 1) {
            System.out.println(ticket.toString() + "插入成功");
        } else {
            System.out.println("插入失败，请检查字段是否匹配");
        }
    }

    @Override
    public CommonResult<TicketDto> queryTicketForPage(Integer page, Integer pageSize) {
        //查询缓存中是否存在该页的信息
        if (redisService.get(JSON_TICKETS_PAGE + page) == null) {
            //参数一是当前页，参数二是每页大小
            IPage<Ticket> ticketPage = new Page<>(page, pageSize);
            ticketPage = ticketDao.selectPage(ticketPage, new QueryWrapper<Ticket>().orderBy(true, true, "rank_offset"));
            List<Ticket> tickets = ticketPage.getRecords();
            String ticketJson = JSON.toJSONString(tickets);
            redisService.set(JSON_TICKETS_PAGE + page, ticketJson);
            //查询数据库中所有数据的条数
            Integer total = ticketDao.selectCount(null);
            TicketDto ticketDto = new TicketDto().setResult(tickets).setTotal(total).setPageNums((total / pageSize) + 1).setPageSize(pageSize);
            return CommonResult.success(ticketDto);
        }
        String ticketsJson = redisService.get(JSON_TICKETS_PAGE + page);
        List<Ticket> tickets = JSON.parseArray(ticketsJson, Ticket.class);
        //查询数据库中所有数据的条数
        Integer total = ticketDao.selectCount(null);
        TicketDto ticketDto = new TicketDto().setResult(tickets).setTotal(total).setPageNums((total / pageSize) + 1).setPageSize(pageSize);
        return CommonResult.success(ticketDto);
    }

    @Override
    public CommonResult<TicketDto> listTicketsBySearch(String searchContext, Integer pageNums, Integer pageSize) {
        //处理搜索内容,让数据库可以识别
        searchContext = "%" + searchContext + "%";
        //前端传送的是页码，我们需要的是第几条记录，将其*pageSize得到结果
        int page = (pageNums-1) * pageSize ;
        //获取数据库总记录数
        Integer searchCount = ticketDao.getSearchCount(searchContext);

        //若查询无结果
        if (searchCount == 0) {
            TicketDto ticketDto = new TicketDto().setResult(null).setTotal(0).setPageSize(pageSize).setPageNums(0).setTotal(0);
            return CommonResult.success(ticketDto);
        }
        //在redis中存储一些常用的搜索词例如城市，如果搜索词在redis中不存在再从数据库中查询
        //reids中缓存样式 json:json:tickets_city:(searchContext)上海(page)1
        if (redisService.get(JSON_TICKETS_SEARCH + searchContext + pageNums) == null) {

            List<Ticket> tickets = ticketDao.listTicketsBySearch(searchContext, page, pageSize);

            //生成响应数据
            TicketDto ticketDto = new TicketDto().setResult(tickets).setPageNums(pageNums).setPageSize(pageSize).setTotal(searchCount);
            return CommonResult.success(ticketDto);
        }

        //从redis中获取结果
        String result = redisService.get(JSON_TICKETS_SEARCH + searchContext + pageNums);
        List<Ticket> tickets = JSON.parseArray(result, Ticket.class);
        TicketDto ticketDto = new TicketDto().setResult(tickets).setPageNums(pageNums).setPageSize(pageSize).setTotal(searchCount);
        return CommonResult.success(ticketDto);
    }

    @Override
    public CommonResult<TicketDto> searchTicketsByEs(String searchContext, Integer page, Integer pageSize) throws IOException {
        if (page<=1){
            page = 1;
        }else {
            page = (page-1)*(pageSize);
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("shop_tickets");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //匹配词条
        BoolQueryBuilder should = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("projectName", searchContext))
                .should(QueryBuilders.matchQuery("city", searchContext))
                .should(QueryBuilders.matchQuery("districtName", searchContext))
                .should(QueryBuilders.matchQuery("projectType", searchContext))
                .should(QueryBuilders.matchQuery("venueName", searchContext));
        //整合builder
        searchSourceBuilder.query(should).from(page).size(pageSize);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //解析结果
        ArrayList<Ticket> list = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            String json = JSON.toJSONString(sourceAsMap);
            Ticket ticket = JSONObject.parseObject(json, Ticket.class);
            list.add(ticket);
        }
        Integer total = Convert.toInt(searchResponse.getHits().getTotalHits().value);
        TicketDto ticketDto = new TicketDto().setResult(list).setTotal(total).setPageNums(page).setPageSize(pageSize);

        return CommonResult.success(ticketDto);

    }


}


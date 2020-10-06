package com.damao.bilibilishop.common.crawler;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.Ticket;
import com.damao.bilibilishop.service.TicketService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 爬取BiliBili贩卖的门票
 *
 * @author 呆毛
 */
@Component
public class BiLiBiLiShopTicket {
    @Autowired
    TicketService ticketService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public CommonResult<Boolean> insertTickets() throws IOException {
        //创建es批量请求
        BulkRequest bulkRequest = new BulkRequest().timeout("2m");

        //经观察发现通过改变url中的page既可以获取不同页面的门票信息
        for (int page = 1; page <= 53; page++) {
            String url = "https://show.bilibili.com/api/ticket/project/listV2?version=134&page=" + page + "&pagesize=16&area=-1&filter=&platform=web&p_type=%E5%85%A8%E9%83%A8%E7%B1%BB%E5%9E%8B";
            //获取ticketJson
            String ticketJson = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
                    .ignoreContentType(true)
                    .execute()
                    .body();
            //将ticketJson转化为java对象
            JSONObject ticketObject = JSON.parseObject(ticketJson);
            JSONObject data = (JSONObject) ticketObject.get("data");
            List results = (List) JSON.parse(data.get("result").toString());

            for (Object result : results) {
                Map ret = JSONObject.parseObject(JSONObject.toJSONString(result), Map.class);
                Ticket ticket = new Ticket()
                        .setId(Convert.toInt(ret.get("id")))
                        .setProjectName(Convert.toStr(ret.get("project_name")))
                        .setProjectType(Convert.toStr(ret.get("project_type")))
                        .setShowTime(Convert.toStr(ret.get("show_time")))
                        .setEndTime(Convert.toStr(ret.get("end_time")))
                        .setTLabel(Convert.toStr(ret.get("tlabel")))
                        .setLabel(Convert.toStr(ret.get("label")))
                        .setSaleFlag(Convert.toStr(ret.get("sale_flag")))
                        .setSalePoint(Convert.toStr(ret.get("sale_point")))
                        .setDistrictName(Convert.toStr(ret.get("district_name")))
                        .setVenueName(Convert.toStr(ret.get("venue_name")))
                        .setRankOffset(Convert.toInt(ret.get("rank_offset")))
                        .setCover(Convert.toStr(ret.get("cover")))
                        .setBanner(Convert.toStr(ret.get("banner")))
                        .setCity(Convert.toStr(ret.get("city")));
                //插入数据库
                ticketService.insertTicket(ticket);
                //插入到es中
                bulkRequest.add(
                    new IndexRequest("shop_tickets")
                        .source(JSON.toJSONString(ticket),XContentType.JSON)
                );
            }
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return CommonResult.success(!bulk.hasFailures());
    }
}

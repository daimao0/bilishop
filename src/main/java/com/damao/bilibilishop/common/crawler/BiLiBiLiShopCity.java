package com.damao.bilibilishop.common.crawler;

import com.alibaba.fastjson.JSONObject;
import com.damao.bilibilishop.module.City;
import com.damao.bilibilishop.module.dto.CitiesDto;
import com.damao.bilibilishop.service.CityService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 爬取哔哩哔哩会员购页面的城市
 *
 * @author 呆毛
 */
@Component
public class BiLiBiLiShopCity {
    @Autowired
    CityService cityService;

    public void insertCities() throws IOException {
        String url = "https://show.bilibili.com/api/ticket/city/list?channel=4";
        //获取cityJson
        String cityJson = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
                .ignoreContentType(true)
                .execute()
                .body();

        //将cityJson转化为java对象
        JSONObject jsonObject = JSONObject.parseObject(cityJson);
        //获取Json中的data属性

        JSONObject dataObject  = jsonObject.getJSONObject("data");
        //将data中的more属性解析,并用ListCities接收
        List<CitiesDto> listCitiesDto = JSONObject.parseArray(dataObject.getJSONArray("more").toJSONString(), CitiesDto.class);
        //将获取到的数据存入数据库中
        for (CitiesDto listCities : listCitiesDto) {
            for (City city : listCities.getList()) {
                cityService.insertCity(city);
            }
        }
    }
}

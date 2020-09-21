package com.damao.bilibilishop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.dao.CityDao;
import com.damao.bilibilishop.module.City;
import com.damao.bilibilishop.module.dto.CitiesDto;
import com.damao.bilibilishop.service.CityService;
import com.damao.bilibilishop.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 呆毛
 */
@Service
public class CityServiceImpl implements CityService {
    public final static String JSON_MAIN_CITIES = "json:main_cities:";
    public final static String JSON_MORE_CITIES = "json:more_cities:";

    @Autowired
    private RedisService redisService;

    @Autowired
    CityDao cityDao;

    @Override
    public void insertCity(City city) {
        int code = cityDao.insert(city);
        if (code == 1) {
            System.out.println(city.toString() + "插入成功");
        } else {
            System.out.println("插入失败，请检查字段是否匹配");
        }
    }

    @Override
    public CommonResult<List<CitiesDto>> listMoreCitiesDto() {
        //如果缓存中没有数据,则从数据库中获取
        if (redisService.get(JSON_MORE_CITIES) == null) {
            //定义一个CitiesDto用于传输数据
            List<CitiesDto> citiesDtoList = new ArrayList<>(4);
            citiesDtoList.add(new CitiesDto().setLetter("ABCD").setList(listCitiesByLetterStr("ABCD")));
            citiesDtoList.add(new CitiesDto().setLetter("EFGH").setList(listCitiesByLetterStr("EFGH")));
            citiesDtoList.add(new CitiesDto().setLetter("JKLM").setList(listCitiesByLetterStr("JKLM")));
            citiesDtoList.add(new CitiesDto().setLetter("NOPQRS").setList(listCitiesByLetterStr("NOPQRS")));
            citiesDtoList.add(new CitiesDto().setLetter("TWXYZ").setList(listCitiesByLetterStr("TWXYZ")));

            //将数据封装成json
            String moreCitiesJson = JSON.toJSONString(citiesDtoList);
            //存储到缓存中
            redisService.set(JSON_MORE_CITIES, moreCitiesJson);
            return CommonResult.success(citiesDtoList);
        }
        //从redis中获取数据
        String moreCitiesJson = redisService.get(JSON_MORE_CITIES);
        //将其转化为java对象
        List<CitiesDto> citiesDtoList = JSONObject.parseArray(moreCitiesJson,CitiesDto.class);
        return CommonResult.success(citiesDtoList);
    }

    @Override
    public CommonResult<List<City>> listMainCitiesDto() {
        //如果缓存中不存在主要城市的json数据
        if (redisService.get(JSON_MAIN_CITIES) == null) {
            //从数据库中获取九个主要城市
            QueryWrapper<City> queryWrapper = new QueryWrapper<>();

            City shanghai = cityDao.selectOne(queryWrapper.eq("name","上海"));
            City chengdu = cityDao.selectOne(queryWrapper.eq("name","成都"));
            City beijing = cityDao.selectOne(queryWrapper.eq("name","北京"));
            City shenzhen = cityDao.selectOne(queryWrapper.eq("name","深圳"));
            City hangzhou = cityDao.selectOne(queryWrapper.eq("name","杭州"));
            City tianjin = cityDao.selectOne(queryWrapper.eq("name","天津"));
            City xiamen = cityDao.selectOne(queryWrapper.eq("name","厦门"));
            City nanjing = cityDao.selectOne(queryWrapper.eq("name","南京"));
            City suzhou = cityDao.selectOne(queryWrapper.eq("name","苏州"));
            List<City> mainCities = new ArrayList<>(10);
            mainCities.add(shanghai);
            mainCities.add(chengdu);
            mainCities.add(beijing);
            mainCities.add(shenzhen);
            mainCities.add(hangzhou);
            mainCities.add(tianjin);
            mainCities.add(xiamen);
            mainCities.add(nanjing);
            mainCities.add(suzhou);

            //将java对象转换成json字符串
            String mainCitiesJson = JSON.toJSONString(mainCities);
            //存储到缓存中
            redisService.set(JSON_MAIN_CITIES, mainCitiesJson);
            return CommonResult.success(mainCities);
        }
        //从缓存中获取数据
        String mainCitiesJson = redisService.get(JSON_MAIN_CITIES);
        //将其转化成java对象
        List<City> mainCities = JSONObject.parseArray(mainCitiesJson,City.class);

        return CommonResult.success(mainCities);
    }


    /**
     * 通过传递城市的首字母字符串获得对应的城市
     * @param letterStr "ABCD"
     * @return  list<City>--->安徽，北京....
     */
    public List<City> listCitiesByLetterStr(String letterStr){
        //将letter字符串分割成数组
        String[] letterArr = letterStr.split("");
        List<City> cities = new ArrayList<>();
        for (String letter:letterArr){
            List<City> queryCities = cityDao.selectList(new QueryWrapper<City>().eq("first_letter", letter));
            cities.addAll(queryCities);
        }
        return cities;
    }
}


package com.damao.bilibilishop.service;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.City;
import com.damao.bilibilishop.module.dto.CitiesDto;

import java.util.List;

/**
 * @author 呆毛
 */
public interface CityService {
    /**
     * 插入城市
     * @param city 传入一个城市
     */
    void insertCity(City city);

    /**
     * 获取城市列表
     * @return
     */
    CommonResult<List<CitiesDto>> listMoreCitiesDto();
    /**
     * 获取主要城市列表
     * @return
     */
    CommonResult<List<City>> listMainCitiesDto();
}

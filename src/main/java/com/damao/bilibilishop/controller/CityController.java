package com.damao.bilibilishop.controller;

import com.damao.bilibilishop.common.api.CommonResult;
import com.damao.bilibilishop.module.City;
import com.damao.bilibilishop.module.dto.CitiesDto;
import com.damao.bilibilishop.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 呆毛
 */
@RestController
@Api(tags = "CityController",description = "获取全国城市")
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping("listMainCities")
    @ApiOperation(value = "获取主要城市")
    public CommonResult<List<City>> listMainCities(){
        return cityService.listMainCitiesDto();
    }

    @GetMapping("listMoreCities")
    @ApiOperation(value = "获取更多城市")
    public CommonResult<List<CitiesDto>> listMoreCities(){
        return cityService.listMoreCitiesDto();
    }
}

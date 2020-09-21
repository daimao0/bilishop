package com.damao.bilibilishop.module.dto;

import com.damao.bilibilishop.module.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 呆毛
 */
@ApiModel(value = "城市列表信息传输")
@Data
@Accessors(chain = true)
public class CitiesDto implements Serializable {
   @ApiModelProperty(value = "获取城市首字母的字符串('ABCD')")
   private String letter;
   @ApiModelProperty(value = "获取城市列表")
   private List<City> list;
}

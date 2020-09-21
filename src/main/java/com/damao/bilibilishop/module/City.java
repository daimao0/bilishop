package com.damao.bilibilishop.module;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@TableName("t_city")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class City implements Serializable {

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "first_letter")
    private String firstLetter;

    @TableField(value = "fullname")
    private String fullName;

    @TableField(value = "name")
    private String name;

    @TableField(value = "num")
    private Integer num;

    @TableField(value = "parent_id")
    private Integer parentId;

    @TableField(value = "type")
    private Integer type;

}

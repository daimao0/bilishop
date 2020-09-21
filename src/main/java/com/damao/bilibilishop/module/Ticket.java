package com.damao.bilibilishop.module;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 呆毛
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_ticket")
public class Ticket {
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "project_name")
    private String projectName;

    @TableField(value = "project_type")
    private String projectType;

    @TableField(value = "show_time")
    private String showTime;

    @TableField(value = "end_time")
    private String endTime;

    @TableField(value = "tlabel")
    private String tLabel;

    @TableField(value = "label")
    private String label;

    @TableField(value = "sale_flag")
    private String saleFlag;

    @TableField(value = "sale_point")
    private String salePoint;

    @TableField(value = "district_name")
    private String districtName;

    @TableField(value = "venue_name")
    private String venueName;

    @TableField(value = "rank_offset")
    private Integer rankOffset;

    @TableField(value = "cover")
    private String cover;

    @TableField(value = "banner")
    private String banner;

    @TableField(value = "city")
    private String city;
}

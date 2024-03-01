package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 种植基地信息表
 * @TableName base_info
 */
@TableName(value ="base_info")
@Data
public class BaseInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer baseId;

    /**
     * 编号 
     */
    private Long baseNum;

    /**
     * 省id
     */
    private Integer provinceId;

    /**
     * 市id
     */
    private Integer cityId;

    /**
     * 区id
     */
    private Integer areaId;

    /**
     * 基地名称
     */
    private String baseName;

    /**
     * 合作单位id 
     */
    private Integer cooperatorId;

    /**
     * 负责人
     */
    private String charge;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 面积 
     */
    private String area;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 适合种植的农作物
     */
    private String cropName;

    /**
     * 基地状态
     */
    private String status;

    /**
     * 排序（级别）
     */
    private Integer level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
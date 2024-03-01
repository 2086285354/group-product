package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 农作物信息表
 * @TableName crop_info
 */
@Data
public class CropInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 编号  
     */
    private Long cropNum;

    /**
     * 种植基地id
     */
    private Integer baseId;

    /**
     * 农作物名称
     */
    private String cropName;

    /**
     * 登记时间
     */
    private Date registrationTime;

    /**
     * 周期
     */
    private String cycle;

    /**
     * 负责人
     */
    private String baseCharge;

    /**
     * 负责人联系方式
     */
    private String basePhone;

    /**
     * 种植方式id
     */
    private Integer planmethodId;

    /**
     * 灌溉方式id
     */
    private Integer irrigationId;

    /**
     * 肥料类型id
     */
    private Integer fertId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除字段（0 未删除  1 已删除）
     */
    private Integer delFlag;

    /**
     * 创建人员
     */
    private String createBy;

    /**
     * 修改人员
     */
    private String updateBy;

    private static final long serialVersionUID = 1L;
}
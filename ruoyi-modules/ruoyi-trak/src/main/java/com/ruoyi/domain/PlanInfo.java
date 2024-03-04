package com.ruoyi.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 农作物种植记录跟踪信息表
 * @TableName plan_info
 */
@Data
@ContentRowHeight(value = 30)
@ColumnWidth(value = 20)
public class PlanInfo implements Serializable {
    /**
     * 农作物记录编号
     */
    @ExcelProperty(value = "农作物记录编号")
    private Integer planId;

    /**
     * 农作物信息编号
     */
    @ExcelProperty(value = "农作物信息编号")
    private String cropNo;

    /**
     * 记录时间
     */
    @ExcelProperty(value = "记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    /**
     * 温度
     */
    @ExcelProperty(value = "温度")
    private String temperature;

    /**
     * 生长状况
     */
    @ExcelProperty(value = "生长状况")
    private String arowths;

    /**
     * 湿度
     */
    @ExcelProperty(value = "湿度")
    private String humidness;

    /**
     * 光照
     */
    @ExcelProperty(value = "光照")
    private String illumination;

    /**
     * 农作物图片
     */
    @ExcelProperty(value = "农作物图片")
    private String pic;

    /**
     * 备注信息（记录观察情况）
     */
    @ExcelProperty(value = "备注信息")
    private String remark;

    /**
     * 农作物信息
     */
    @ExcelIgnore
    private CropInfo cropInfo;

    /**
     * 删除字段（0 未删除  1 已删除）
     */
    @ExcelIgnore
    private Integer delFlag;
    @ExcelProperty(value = "删除状态")
    private String delStr;

    /**
     * 创建人员
     */
    @ExcelProperty(value = "创建人员")
    private String createBy;

    /**
     * 修改人员
     */
    @ExcelProperty(value = "修改人员")
    private String updateBy;

    private static final long serialVersionUID = 1L;
}
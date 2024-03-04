package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName recover_his
 */
@Data
public class RecoverHis implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 申请编号
     */
    private String recoverNo;

    /**
     * 申请账号
     */
    private String username;

    /**
     * 申请人
     */
    private String user;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 审批状态
     */
    private Integer status;

    /**
     * 审批信息
     */
    private String result;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /**
     * 审批人
     */
    private String completeBy;

    private static final long serialVersionUID = 1L;
}
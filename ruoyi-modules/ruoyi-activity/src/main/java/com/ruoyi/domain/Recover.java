package com.ruoyi.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName recover
 */
@Data
public class Recover implements Serializable {
    /**
     * 申请编号
     */
    private String no;

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
     * 审批状态（0 审批中；1 审批通过；2 审批拒绝）
     */
    private Integer status;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 审核信息
     */
    private String result;

    private static final long serialVersionUID = 1L;
}
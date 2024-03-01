package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName dgtx_places
 */
@TableName(value ="dgtx_places")
@Data
public class DgtxPlaces implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer parentId;

    /**
     * 
     */
    private String cname;

    /**
     * 
     */
    private Integer ctype;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
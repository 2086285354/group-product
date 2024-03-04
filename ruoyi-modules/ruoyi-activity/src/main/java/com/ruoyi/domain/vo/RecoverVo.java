package com.ruoyi.domain.vo;

import com.ruoyi.domain.Recover;
import lombok.Data;

@Data
public class RecoverVo extends Recover {
    private String taskId;
    private String taskName;
    private String processInstanceId;
    private String assignee;
    private Integer flag;
}

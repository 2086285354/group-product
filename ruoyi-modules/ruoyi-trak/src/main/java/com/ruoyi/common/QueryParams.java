package com.ruoyi.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
public class QueryParams {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String cropName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}

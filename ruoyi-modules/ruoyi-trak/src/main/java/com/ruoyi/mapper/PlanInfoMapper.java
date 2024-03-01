package com.ruoyi.mapper;

import com.ruoyi.common.QueryParams;
import com.ruoyi.domain.PlanInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86132
* @description 针对表【plan_info(农作物种植记录跟踪信息表)】的数据库操作Mapper
* @createDate 2024-02-28 11:53:11
* @Entity com.ruoyi.domain.PlanInfo
*/
public interface PlanInfoMapper extends BaseMapper<PlanInfo> {

    List<PlanInfo> selectPlanInfoList(QueryParams params);

    void addPlanInfo(PlanInfo info);

    void updatePlanCropInfo(PlanInfo info);

    void deletePlanCropInfoByIds(String ids);

    List<PlanInfo> selectPlanInfoToExcel(QueryParams params);

    void saveBach(List<PlanInfo> list);
}





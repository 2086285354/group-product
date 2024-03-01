package com.ruoyi.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.QueryParams;
import com.ruoyi.domain.PlanInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
* @author 86132
* @description 针对表【plan_info(农作物种植记录跟踪信息表)】的数据库操作Service
* @createDate 2024-02-28 11:53:11
*/
public interface PlanInfoService extends IService<PlanInfo> {

    PageInfo<PlanInfo> selectPlanInfoList(QueryParams params);

    String uploadPicture(MultipartFile file);

    void addPlanInfo(PlanInfo info);

    void updatePlanCropInfo(PlanInfo info);

    void deletePlanCropInfoByIds(String ids);

    void excelDownload(HttpServletResponse response,QueryParams params) throws IOException, URISyntaxException, InterruptedException;

    boolean importPlanCropInfoData(MultipartFile file);

    void savaBach(List<PlanInfo> list);
}

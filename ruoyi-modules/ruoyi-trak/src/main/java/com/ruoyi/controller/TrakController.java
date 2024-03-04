package com.ruoyi.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.QueryParams;
import com.ruoyi.common.R;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.domain.CropInfo;
import com.ruoyi.domain.PlanInfo;
import com.ruoyi.service.BaseInfoService;
import com.ruoyi.service.CropInfoService;
import com.ruoyi.service.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("trak")
public class TrakController {
    @Autowired
    BaseInfoService baseInfoService;
    @Autowired
    CropInfoService cropInfoService;
    @Autowired
    PlanInfoService planInfoService;
    @Autowired
    HttpServletResponse response;

    /**
     * 查询种植记录
     * @param params
     * @return
     */
    @GetMapping("getPlanInfoList")
    public R getPlanInfoList(QueryParams params){
        PageInfo<PlanInfo> info =  planInfoService.selectPlanInfoList(params);
        return R.success(info);
    }

    /**
     * 上传作物图片
     * @param file
     * @return
     */
    @RequestMapping("upload")
    @CrossOrigin
    public R upload(MultipartFile file){
        String imgUrl = planInfoService.uploadPicture(file);
        return R.success(imgUrl);
    }

    /**
     * 查询农作物信息
     * @return
     */
    @GetMapping("getCropInfoList")
    public R getCropInfoList(){
        List<CropInfo> list = cropInfoService.selectCropInfoList();
        return R.success(list);
    }

    /**
     * 添加种植记录
     * @param info
     * @return
     */
    @PostMapping("addPlanCropInfo")
    public R addPlanCropInfo(@RequestBody PlanInfo info){
        planInfoService.addPlanInfo(info);
        return R.success();
    }

    /**
     * 修改种植记录
     * @param info
     * @return
     */
    @PutMapping("updatePlanCropInfo")
    public R updatePlanCropInfo(@RequestBody PlanInfo info){
        planInfoService.updatePlanCropInfo(info);
        return R.success();
    }

    /**
     * 删除种植记录
     * @param ids
     * @return
     */
    @DeleteMapping("deletePlanCropInfo")
    public R deletePlanCropInfo(String ids){
        planInfoService.deletePlanCropInfoByIds(ids);
        return R.success();
    }

    /**
     * 导出种植记录
     * @param params
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("excelDownload")
    public void excelDownload(QueryParams params) throws IOException, URISyntaxException, InterruptedException {
        planInfoService.excelDownload(response,params);
    }

    /**
     * 导入数据
     * @param file
     * @return
     */
    @PostMapping("importPlanCropInfoData")
    public R importPlanCropInfoData(MultipartFile file){
        boolean b = planInfoService.importPlanCropInfoData(file);
        return R.success(b);
    }

}

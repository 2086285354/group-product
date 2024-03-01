package com.ruoyi.service;

import com.ruoyi.domain.CropInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86132
* @description 针对表【crop_info(农作物信息表)】的数据库操作Service
* @createDate 2024-02-28 11:53:11
*/
public interface CropInfoService extends IService<CropInfo> {

    List<CropInfo> selectCropInfoList();
}

package com.ruoyi.mapper;

import com.ruoyi.domain.CropInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86132
* @description 针对表【crop_info(农作物信息表)】的数据库操作Mapper
* @createDate 2024-02-28 11:53:11
* @Entity com.ruoyi.domain.CropInfo
*/
public interface CropInfoMapper extends BaseMapper<CropInfo> {

    List<CropInfo> selectCropInfoList();
}





package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.domain.CropInfo;
import com.ruoyi.service.CropInfoService;
import com.ruoyi.mapper.CropInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86132
* @description 针对表【crop_info(农作物信息表)】的数据库操作Service实现
* @createDate 2024-02-28 11:53:11
*/
@Service
public class CropInfoServiceImpl extends ServiceImpl<CropInfoMapper, CropInfo>
    implements CropInfoService{

    @Override
    public List<CropInfo> selectCropInfoList() {
        return baseMapper.selectCropInfoList();
    }
}





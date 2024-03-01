package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.domain.DgtxPlaces;
import com.ruoyi.mapper.DgtxPlacesMapper;
import com.ruoyi.service.DgtxPlacesService;
import org.springframework.stereotype.Service;

/**
* @author 86132
* @description 针对表【dgtx_places】的数据库操作Service实现
* @createDate 2024-02-22 21:02:02
*/
@Service
public class DgtxPlacesServiceImpl extends ServiceImpl<DgtxPlacesMapper, DgtxPlaces>
    implements DgtxPlacesService {

    @Override
    public void del(String id) {
        baseMapper.deleteById(id);
    }
}





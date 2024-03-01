package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.domain.DgtxPlaces;

/**
* @author 86132
* @description 针对表【dgtx_places】的数据库操作Service
* @createDate 2024-02-22 21:02:02
*/
public interface DgtxPlacesService extends IService<DgtxPlaces> {

    void del(String id);
}

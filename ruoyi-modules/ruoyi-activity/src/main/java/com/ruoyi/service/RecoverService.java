package com.ruoyi.service;

import com.ruoyi.domain.Recover;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.domain.RecoverHis;
import com.ruoyi.domain.vo.RecoverVo;

import java.util.List;

/**
* @author 86132
* @description 针对表【recover】的数据库操作Service
* @createDate 2024-03-03 10:22:43
*/
public interface RecoverService extends IService<Recover> {

    List<RecoverVo> selectRecoverList();

    void saveRecover(Recover recover);

    void completeRecover(RecoverVo vo);

    List<RecoverHis> selectRecoverHistorys();
}

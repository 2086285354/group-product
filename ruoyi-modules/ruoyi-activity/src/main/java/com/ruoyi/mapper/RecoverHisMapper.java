package com.ruoyi.mapper;

import com.ruoyi.domain.RecoverHis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86132
* @description 针对表【recover_his】的数据库操作Mapper
* @createDate 2024-03-03 10:22:43
* @Entity com.ruoyi.domain.RecoverHis
*/
public interface RecoverHisMapper extends BaseMapper<RecoverHis> {

    List<RecoverHis> selectRecoverHistorys(String user);
}





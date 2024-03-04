package com.ruoyi.mapper;

import com.ruoyi.domain.Recover;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.domain.vo.RecoverVo;

/**
* @author 86132
* @description 针对表【recover】的数据库操作Mapper
* @createDate 2024-03-03 10:22:43
* @Entity com.ruoyi.domain.Recover
*/
public interface RecoverMapper extends BaseMapper<Recover> {

    Recover selectRecoverById(String no);

    void saveRecover(Recover recover);

    void updateResult(RecoverVo vo);

    Recover selectByUserName(String username);
}





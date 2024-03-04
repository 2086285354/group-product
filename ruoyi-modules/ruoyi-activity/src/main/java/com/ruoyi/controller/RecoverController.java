package com.ruoyi.controller;

import com.ruoyi.common.R;
import com.ruoyi.domain.Recover;
import com.ruoyi.domain.RecoverHis;
import com.ruoyi.domain.vo.RecoverVo;
import com.ruoyi.service.RecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("recover")
public class RecoverController {
    @Autowired
    RecoverService recoverService;

    /**
     * 查询代办任务
     * @return
     */
    @GetMapping("list")
    public R list() {
        List<RecoverVo> list = recoverService.selectRecoverList();
        return R.success(list);
    }

    /**
     * 提交账号申请
     */
    @PostMapping("addRecover")
    public R addRecover(@RequestBody Recover recover){
        recoverService.saveRecover(recover);
        return R.success();
    }

    /**
     * 完成申请
     */
    @PostMapping("completeRecover")
    public R completeRecover(@RequestBody RecoverVo vo){
        recoverService.completeRecover(vo);
        return R.success();
    }

    /**
     * 获取当前用户的恢复账号申请记录
     */
    @GetMapping("getRecoverHistory")
    public R getRecoverHistory(){
        List<RecoverHis> list = recoverService.selectRecoverHistorys();
        return R.success(list);
    }
}

package com.ruoyi.service;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserProfileService {
    @GetMapping("/user/profile/getLoginUsername")
    public AjaxResult getLoginUsername();

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @PostMapping("/user/getUserByUsername")
    public R<LoginUser> getUserByUsername(@RequestParam("username") String username);
    @PostMapping("/user/recoverUser")
    AjaxResult recoverUser(@RequestParam("username")String username);
}

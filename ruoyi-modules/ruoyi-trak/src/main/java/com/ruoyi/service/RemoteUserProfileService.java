package com.ruoyi.service;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserProfileService {
    @GetMapping("/user/profile/getLoginUsername")
    public AjaxResult getLoginUsername();
}

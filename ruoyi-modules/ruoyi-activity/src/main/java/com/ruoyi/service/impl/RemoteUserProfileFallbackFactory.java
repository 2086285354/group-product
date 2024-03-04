package com.ruoyi.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.service.RemoteUserProfileService;
import com.ruoyi.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemoteUserProfileFallbackFactory implements FallbackFactory<RemoteUserProfileService> {

    @Override
    public RemoteUserProfileService create(Throwable cause) {
        log.error("用户服务调用失败:{}", cause.getMessage());
        return new RemoteUserProfileService() {
            @Override
            public AjaxResult getLoginUsername() {
                return AjaxResult.error("获取用户名失败");
            }

            @Override
            public R<LoginUser> getUserByUsername(String username) {
                return R.fail("获取用户失败:" + cause.getMessage());
            }

            @Override
            public AjaxResult recoverUser(String username) {
                return AjaxResult.error("用户恢复失败");
            }
        };
    }
}

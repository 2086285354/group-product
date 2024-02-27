package com.ruoyi.auth.controller;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.auth.common.ZzyConfig;
import com.ruoyi.common.core.exception.ServiceException;
import com.zhenzi.sms.ZhenziSmsClient;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.auth.form.LoginBody;
import com.ruoyi.auth.form.RegisterBody;
import com.ruoyi.auth.service.SysLoginService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.auth.AuthUtil;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token 控制
 * 
 * @author ruoyi
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ZzyConfig zzyConfig;

    @GetMapping("getPhoneCode")
    public R<?> getPhoneCode(String phonenumber) throws Exception {
        ZhenziSmsClient client = new ZhenziSmsClient(zzyConfig.getApiUrl(), zzyConfig.getAppId(), zzyConfig.getAppSecret());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phonenumber);
        params.put("templateId", zzyConfig.getTemplateId());
        String[] templateParams = new String[2];

        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);

        String code = lineCaptcha.getCode();

        System.err.println("验证码："+code);

        redisTemplate.opsForValue().set("zzy:"+phonenumber,code,5, TimeUnit.MINUTES);

        templateParams[0] = code;
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);
        String result = client.send(params);
        JSONObject jsonObject = JSON.parseObject(result);
        String reCode = jsonObject.get("code") + "";
        if (!reCode.equals("0")){
            throw new ServiceException("验证码发送失败，请检查手机号码是否正确");
        }
        return R.ok();
    }
    @PostMapping("phone")
    public R<?> phone(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.phoneLogin(form.getPhonenumber(), form.getCode());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }
}

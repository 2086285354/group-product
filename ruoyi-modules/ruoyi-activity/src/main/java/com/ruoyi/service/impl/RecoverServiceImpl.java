package com.ruoyi.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.domain.Recover;
import com.ruoyi.domain.RecoverHis;
import com.ruoyi.domain.vo.RecoverVo;
import com.ruoyi.mapper.RecoverHisMapper;
import com.ruoyi.service.RecoverService;
import com.ruoyi.mapper.RecoverMapper;
import com.ruoyi.service.RemoteUserProfileService;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
import java.util.*;

/**
* @author 86132
* @description 针对表【recover】的数据库操作Service实现
* @createDate 2024-03-03 10:22:43
*/
@Service
public class RecoverServiceImpl extends ServiceImpl<RecoverMapper, Recover>
    implements RecoverService{

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    RemoteUserProfileService remoteUserProfileService;

    @Resource
    RecoverHisMapper recoverHisMapper;

    @Override
    public List<RecoverVo> selectRecoverList() {
        AjaxResult login = remoteUserProfileService.getLoginUsername();

        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("recover")
                .taskAssignee(login.get("msg")+"")
                .list();
        List<RecoverVo> list = new ArrayList<>();

        for (Task task : tasks) {
            RecoverVo vo = new RecoverVo();
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getName());
            vo.setProcessInstanceId(task.getProcessInstanceId());
            vo.setAssignee(task.getAssignee());

            HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();

            Recover recover = baseMapper.selectRecoverById(instance.getBusinessKey());

            vo.setNo(recover.getNo());
            vo.setUsername(recover.getUsername());
            vo.setUser(vo.getUser());
            vo.setCreateTime(recover.getCreateTime());
            vo.setStatus(recover.getStatus());
            vo.setReason(recover.getReason());
            vo.setResult(recover.getResult());

            list.add(vo);
        }
        return list;
    }

    @Override
    public void saveRecover(Recover recover) {
        AjaxResult loginUsername = remoteUserProfileService.getLoginUsername();
        String user = loginUsername.get("msg") + "";

        R<LoginUser> userInfo = remoteUserProfileService.getUserByUsername(recover.getUsername());

        if (StringUtils.isNull(userInfo) || StringUtils.isNull(userInfo.getData())) {
            throw new ServiceException("申请账号：" + recover.getUsername() + " 未被删除");
        }

        Recover recoverDB = baseMapper.selectByUserName(recover.getUsername());
        if (recoverDB != null){
            throw new ServiceException("申请账号："+recover.getUsername()+" 正在审核中请勿重复申请");
        }
        String no = new Snowflake().nextIdStr();

        recover.setUsername(recover.getUsername());
        recover.setNo(no);
        recover.setUser(user);

        baseMapper.saveRecover(recover);

        Map<String,Object> map = new HashMap<>();
        map.put("normal","ry");
        map.put("root","admin");
        runtimeService.startProcessInstanceByKey("recover",no,map);
    }

    @Override
    public void completeRecover(RecoverVo vo) {
        AjaxResult loginUsername = remoteUserProfileService.getLoginUsername();
        String user = loginUsername.get("msg") + "";//当前登录对象

        if (!user.equals(vo.getAssignee())){
            throw new ServiceException("用户任务不匹配，请联系管理员处理");
        }

        Map<String,Object> map = new HashMap<>();

        if (vo.getFlag()!=null){
            map.put("flag",vo.getFlag());
            if (vo.getFlag()==0){
                vo.setStatus(1);
                AjaxResult result = remoteUserProfileService.recoverUser(vo.getUsername());
            }
            if (vo.getFlag()==1){
                vo.setStatus(2);
            }
            baseMapper.updateResult(vo);
        }
        RecoverHis recoverHis = new RecoverHis();
        recoverHis.setRecoverNo(vo.getNo());
        recoverHis.setCompleteBy(vo.getAssignee());
        recoverHis.setCompleteTime(new Date());
        recoverHisMapper.insert(recoverHis);
        taskService.complete(vo.getTaskId(),map);
    }

    @Override
    public List<RecoverHis> selectRecoverHistorys() {
        AjaxResult loginUsername = remoteUserProfileService.getLoginUsername();
        String user = loginUsername.get("msg") + "";//当前登录对象
        return recoverHisMapper.selectRecoverHistorys(user);
    }
}





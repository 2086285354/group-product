package com.ruoyi.controller;



import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.domain.DgtxPlaces;
import com.ruoyi.service.DgtxPlacesService;
import com.ruoyi.utils.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("places")
public class ListController {
    @Autowired
    DgtxPlacesService dgtxPlacesService;
    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("list")
    public Result list(){
        List<DgtxPlaces> list = null;
        List<Tree<String>> treeNodes = null;
        if (redisTemplate.hasKey("placeList")){
            list = redisTemplate.opsForList().range("placeList",0,-1);
        }else {
            list =dgtxPlacesService.list();
            redisTemplate.opsForList().rightPushAll("placeList",list);
            redisTemplate.expire("placeList", RandomUtil.randomInt(1,5), TimeUnit.HOURS);
        }
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();

        //转换器 (含义:找出父节点为字符串零的所有子节点, 并递归查找对应的子节点, 深度最多为 3)
        treeNodes = TreeUtil.build(list, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId()+"");
                    tree.setParentId(treeNode.getParentId()+"");
                    tree.setName(treeNode.getCname());
                });
        return Result.success(treeNodes);
    }

    @RequestMapping("add")
    public void add(@RequestBody DgtxPlaces dgtxPlaces){
        dgtxPlacesService.saveOrUpdate(dgtxPlaces);
    }

    @RequestMapping("del")
    public void del(String id){
        dgtxPlacesService.del(id);
    }



}

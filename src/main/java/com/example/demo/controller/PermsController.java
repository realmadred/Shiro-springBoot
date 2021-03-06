package com.example.demo.controller;

import com.example.demo.entity.common.Page;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @auther lf
 * @date 2017/9/26
 * @description 权限管理
 */
@RestController
@RequestMapping("/perms")
public class PermsController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/list")
    public Map<String,Object> list() throws Exception {
        final Page page = getPage();
        final List<Map<String, Object>> maps = permissionService.findByPage(page);
        return success(maps);
    }

    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody Map<String,Object> data) throws Exception {
        final int i = permissionService.addPermission(data);
        if (i>0){
            return success(i);
        }else {
            return fail("添加权限失败！");
        }
    }

    @PostMapping("/del")
    public Map<String,Object> del(Integer id) throws Exception {
        final int i = permissionService.delPermissionById(id);
        if (i>0){
            return success();
        }else {
            return fail("添加权限失败！");
        }
    }

}

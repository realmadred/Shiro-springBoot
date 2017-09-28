package com.example.demo.controller;

import com.example.demo.entity.common.Page;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Map<String,Object> add() throws Exception {

        return success(null);
    }

}

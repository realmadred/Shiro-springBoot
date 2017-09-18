package com.example.demo.service.impl;

import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 描述
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有的权限
     */
    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "select id,name,parent_id,permission,url" +
                " FROM sys_permission WHERE available = 'true'";
        return jdbcTemplate.queryForList(sql);
    }
}

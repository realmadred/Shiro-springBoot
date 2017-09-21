package com.example.demo.service.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.entity.jdbc.Condition;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
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

    @Resource
    private BaseDao baseDao;

    public static final String SYS_PERMISSION = "sys_permission";

    /**
     * 查询所有的权限
     */
    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "SELECT id,name,parent_id,permission,url" +
                " FROM sys_permission WHERE available = 'true'";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 添加权限
     *
     * @param perm 权限表达式
     */
    @Override
    public void addPermission(final Map<String, Object> perm) {
        baseDao.add(SYS_PERMISSION,perm);
    }

    /**
     * 删除权限
     *
     * @param perm 权限表达式
     */
    @Override
    public void delPermission(final Map<String, Object> perm) {
        baseDao.delete(SYS_PERMISSION, Condition.create().addEqConditions(perm));
    }

    /**
     * 添加多个权限
     *
     * @param perms 权限表达式
     */
    @Override
    public void addPermissions(final List<Map<String, Object>> perms) {
        if (CollectionUtils.isEmpty(perms)) return;
        perms.forEach(map -> baseDao.add(SYS_PERMISSION,map));
    }

    /**
     * 删除多个权限
     *
     * @param perms 权限表达式
     */
    @Override
    public void delPermissions(final List<Map<String, Object>> perms) {
        if (CollectionUtils.isEmpty(perms)) return;
        perms.forEach(map -> baseDao.delete(SYS_PERMISSION,Condition.create().addEqConditions(map)));
    }
}

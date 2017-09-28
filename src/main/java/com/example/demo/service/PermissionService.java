package com.example.demo.service;

import com.example.demo.entity.common.Page;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * 查询所有的权限
     */
    List<Map<String, Object>> findAll();

    /**
     * 分页查询权限
     */
    List<Map<String, Object>> findByPage(Page page);

    /**
     * 添加权限
     * @param perm 权限表达式
     */
    int addPermission(Map<String,Object> perm);

    /**
     * 删除权限
     * @param perm 权限表达式
     */
    void delPermission(Map<String,Object> perm);

    /**
     * 修改权限
     * @param perm 权限表达式
     */
    void updatePermissionById(Map<String,Object> perm);

    /**
     * 添加多个权限
     * @param perms 权限表达式
     */
    void addPermissions(List<Map<String,Object>> perms);

    /**
     * 删除多个权限
     * @param perms 权限表达式
     */
    void delPermissions(List<Map<String,Object>> perms);

    /**
     * 删除多个权限
     * @param ids 权限id集合
     */
    void delPermissionsByIds(Object[] ids);

    /**
     * 重新加载权限
     * lf
     * 2017-09-21 10:26:03
     */
    void reloadPermissions();
}
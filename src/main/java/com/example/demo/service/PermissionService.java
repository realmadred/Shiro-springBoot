package com.example.demo.service;


import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * 查询所有的权限
     */
    List<Map<String, Object>> findAll();

    /**
     * 添加权限
     * @param perm 权限表达式
     */
    void addPermission(Map<String,Object> perm);

    /**
     * 删除权限
     * @param url url
     * @param perm 权限表达式
     */
    void delPermission(Map<String,Object> perm);

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
}
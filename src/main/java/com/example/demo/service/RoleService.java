package com.example.demo.service;


import java.util.List;
import java.util.Map;

/**
 * 角色服务
 */
public interface RoleService extends BaseService {


    /**通过角色id查找用户信息*/
    List<Map<String, Object>> findPermissionsById(Integer id);
}
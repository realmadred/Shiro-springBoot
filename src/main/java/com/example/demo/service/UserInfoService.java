package com.example.demo.service;


import java.util.List;
import java.util.Map;

public interface UserInfoService extends BaseService {

    /**通过username查找用户信息*/
    Map<String, Object> findByUsername(String username);

    /**通过id查找用户信息*/
    Map<String, Object> findById(Integer id);

    /**通过用户id查找用户信息*/
    List<Map<String, Object>> findRolesById(Integer id);
}
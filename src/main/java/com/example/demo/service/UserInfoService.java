package com.example.demo.service;


import java.util.List;
import java.util.Map;

public interface UserInfoService {

    /**通过username查找用户信息*/
    Map<String, Object> findByUsername(String username);

    /**通过用户id查找用户信息*/
    List<Map<String, Object>> findRolesById(Integer id);
}
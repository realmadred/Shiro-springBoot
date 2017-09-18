package com.example.demo.service;


import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * 查询所有的权限
     */
    List<Map<String, Object>> findAll();
}
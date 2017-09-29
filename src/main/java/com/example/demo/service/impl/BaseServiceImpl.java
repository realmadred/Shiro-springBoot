package com.example.demo.service.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @auther lf
 * @date 2017/9/29
 * @description 基础service实现
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BaseDao baseDao;

}

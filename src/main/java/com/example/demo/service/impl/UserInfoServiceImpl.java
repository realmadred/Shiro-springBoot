package com.example.demo.service.impl;

import com.example.demo.service.UserInfoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        String sql = "select id,name,username,password,salt,state FROM user_info WHERE username = ? LIMIT 1";
        return jdbcTemplate.queryForMap(sql,username);
    }

    /**
     * 通过用户id查询用户角色
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> findRolesById(final Integer id) {
        String sql = "select r.id,r.available,r.description,r.role FROM sys_user_role ur " +
                " JOIN sys_role r on r.id = ur.role_id WHERE ur.uid = ?";
        return jdbcTemplate.queryForList(sql,id);
    }
}
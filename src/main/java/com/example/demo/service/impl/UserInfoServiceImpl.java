package com.example.demo.service.impl;

import com.example.demo.service.UserInfoService;
import com.example.demo.util.Common;
import com.example.demo.util.Tables;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {

    private static final String FIELDS = "id,name,username,password,salt,state";

    @Override
    public Map<String, Object> findByUsername(String username) {
        if (StringUtils.isBlank(username)) return Common.EMPTY_MAP;
        String sql = "select "+FIELDS+" FROM user_info WHERE username = ? LIMIT 1";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, username);
        if (CollectionUtils.isEmpty(maps)) return Common.EMPTY_MAP;
        return maps.get(0);
    }

    /**
     * 通过id查找用户信息
     *
     * @param id
     */
    @Override
    public Map<String, Object> findById(final Integer id) {
        return baseDao.findById(Tables.USER_INFO,id,"id,username");
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
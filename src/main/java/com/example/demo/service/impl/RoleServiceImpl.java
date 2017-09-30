package com.example.demo.service.impl;

import com.example.demo.service.RoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 描述
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl  implements RoleService {

    /**
     * 通过角色id查找用户信息
     *
     * @param id
     */
    @Override
    @Cacheable(value = "role",keyGenerator = "keyGenerator")
    public List<Map<String, Object>> findPermissionsById(final Integer id) {
        String sql = "select p.id,p.available,p.name,p.parent_id,p.permission,p.resource_type,p.url" +
                " FROM sys_role_permission rp " +
                " JOIN sys_permission p on p.id = rp.permission_id WHERE rp.role_id = ?";
        return jdbcTemplate.queryForList(sql,id);
    }
}

package com.example.demo.service.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.entity.common.Page;
import com.example.demo.entity.jdbc.Condition;
import com.example.demo.entity.jdbc.QueryCondition;
import com.example.demo.service.PermissionService;
import com.example.demo.util.Common;
import com.example.demo.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.example.demo.util.Constant.ANON_CHAIN;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 权限服务
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private BaseDao baseDao;

    @Resource
    private ShiroFilterFactoryBean filterFactoryBean;

    private static final String SYS_PERMISSION = "sys_permission";
    private static final String FIELDS = "id,name,parent_id,permission,url";

    /**
     * 查询所有的权限
     */
    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "SELECT " +FIELDS+
                " FROM sys_permission WHERE available = 'true'";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 分页查询权限
     *
     * @param page
     */
    @Override
    public List<Map<String, Object>> findByPage(final Page page) {
        if (page == null) return null;
        return baseDao.find(SYS_PERMISSION, FIELDS,
                QueryCondition.create(page.getPage(),page.getPageSize())
                        .addCondition("available","true"));
    }

    /**
     * 添加权限
     *
     * @param perm 权限表达式
     */
    @Override
    public int addPermission(final Map<String, Object> perm) {
        String permission = Common.getMapString(perm, "permission");
        if (StringUtils.isBlank(permission)) return 0;
        if (!permission.startsWith(Constant.PERMS_PREFIX)) {
            permission = StringUtils.join(Constant.PERMS_PREFIX,permission,Constant.SECTION_SUFFIX);
            perm.put("permission",permission);
        }
        final int i = baseDao.add(SYS_PERMISSION, perm);
        if (i > 0) {
            final DefaultFilterChainManager filterChainManager = getFilterChainManager();
            final Map<String, NamedFilterList> filterChains = filterChainManager.getFilterChains();
            filterChains.remove(Constant.ALL_CHAIN);
            filterChainManager.createChain(Common.getMapString(perm, "url"), permission);
            filterChainManager.createChain(Constant.ALL_CHAIN, Constant.AUTHC_CHAIN);
        }
        return i;
    }

    /**
     * 删除权限
     *
     * @param perm 权限表达式
     */
    @Override
    public void delPermission(final Map<String, Object> perm) {
        final int i = baseDao.delete(SYS_PERMISSION, Condition.create().addEqConditions(perm));
        if (i > 0) {
            reloadPermissions();
        }
    }

    /**
     * 修改权限,需要id
     *
     * @param perm 权限表达式
     */
    @Override
    public void updatePermissionById(final Map<String, Object> perm) {
        final int i = baseDao.updateById(SYS_PERMISSION, perm);
        if (i > 0) reloadPermissions();
    }

    /**
     * 添加多个权限
     *
     * @param perms 权限表达式
     */
    @Override
    public void addPermissions(final List<Map<String, Object>> perms) {
        if (CollectionUtils.isEmpty(perms)) return;
        perms.forEach(map -> baseDao.add(SYS_PERMISSION, map));
        reloadPermissions();
    }

    /**
     * 删除多个权限
     *
     * @param perms 权限表达式
     */
    @Override
    public void delPermissions(final List<Map<String, Object>> perms) {
        if (CollectionUtils.isEmpty(perms)) return;
        perms.forEach(map -> baseDao.delete(SYS_PERMISSION, Condition.create().addEqConditions(map)));
        reloadPermissions();
    }

    /**
     * 删除多个权限
     *
     * @param ids 权限id集合
     */
    @Override
    public void delPermissionsByIds(final Object[] ids) {
        baseDao.deleteByIds(SYS_PERMISSION, ids);
        reloadPermissions();
    }

    /**
     * lf
     * 2017-09-21 10:20:15
     * 动态加载所有的权限
     * 需要同步方法
     */
    public void reloadPermissions() {
        try {
            final DefaultFilterChainManager filterManager = getFilterChainManager();
            synchronized (this){
                //清空拦截管理器中的存储
                filterManager.getFilterChains().clear();
                // 配置不会被拦截的链接 顺序判断
                filterManager.createChain("/static/**", ANON_CHAIN);
                filterManager.createChain("/html/**", ANON_CHAIN);
                filterManager.createChain("/fonts/**", ANON_CHAIN);
                filterManager.createChain("/css/**", ANON_CHAIN);
                filterManager.createChain("/js/**", ANON_CHAIN);
                filterManager.createChain("/i/**", ANON_CHAIN);
                filterManager.createChain("/img/**", ANON_CHAIN);
                filterManager.createChain("/*.psd", ANON_CHAIN);
                // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
                filterManager.createChain("/logout", Constant.LOGOUT_CHAIN);

                final List<Map<String, Object>> maps = findAll();
                if (!CollectionUtils.isEmpty(maps)) {
                    maps.forEach(map -> {
                        final String permission = Common.getMapString(map, "permission");
                        final String url = Common.getMapString(map, "url");
                        if (!Common.hasEmpty(permission, url)) {
                            filterManager.createChain(url.trim(), permission.trim());
                        }
                    });
                }
                //放在最后
                filterManager.createChain(Constant.ALL_CHAIN, Constant.AUTHC_CHAIN);
            }
        } catch (Exception e) {
            LOGGER.error("updatePermission error", e);
        }
    }

    private synchronized DefaultFilterChainManager getFilterChainManager() {
        try {
            AbstractShiroFilter shiroFilter = (AbstractShiroFilter) filterFactoryBean.getObject();
            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            return (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

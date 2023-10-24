package com.mszlu.xt.admin.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.xt.admin.dao.AdminUserMapper;
import com.mszlu.xt.admin.dao.data.AdminUser;
import com.mszlu.xt.admin.domain.AdminUserDomain;
import com.mszlu.xt.admin.params.AdminUserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AdminUserDomainRepository {

    @Resource
    private AdminUserMapper adminUserMapper;

    public AdminUserDomain createDomain(AdminUserParam adminUserParam) {
        return new AdminUserDomain(this,adminUserParam);
    }

    public AdminUser findUserByUsername(String username) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUsername,username).last("limit 1");
        return adminUserMapper.selectOne(queryWrapper);
    }
}
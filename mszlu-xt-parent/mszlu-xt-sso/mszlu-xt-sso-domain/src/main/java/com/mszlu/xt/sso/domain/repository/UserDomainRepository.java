package com.mszlu.xt.sso.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.xt.model.params.UserParam;
import com.mszlu.xt.sso.dao.UserMapper;
import com.mszlu.xt.sso.dao.data.User;
import com.mszlu.xt.sso.domain.UserDomain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDomainRepository {
    @Resource
    private UserMapper userMapper;

    public UserDomain createDomain(UserParam userParam) {
        return new UserDomain(this, userParam);
    }

    public User findUserByUnionId(String unionId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //limit 1是对应的一个优化，查到数据就不再检索了
        queryWrapper.eq(User::getUnionId, unionId).last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    public User findUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    public void saveUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getLastLoginTime, user.getLastLoginTime());
        updateWrapper.eq(User::getId, user.getId());
        userMapper.update(null, updateWrapper);
    }


}

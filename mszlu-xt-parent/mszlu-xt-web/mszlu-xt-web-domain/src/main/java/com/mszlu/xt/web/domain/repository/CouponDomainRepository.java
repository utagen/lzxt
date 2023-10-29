package com.mszlu.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mszlu.xt.pojo.Coupon;
import com.mszlu.xt.pojo.UserCoupon;
import com.mszlu.xt.web.dao.CouponMapper;
import com.mszlu.xt.web.dao.UserCouponMapper;
import com.mszlu.xt.web.domain.CouponDomain;
import com.mszlu.xt.web.model.params.CouponParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CouponDomainRepository {
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private CouponMapper couponMapper;

    public CouponDomain createDomain(CouponParam couponParam) {
        return new CouponDomain(this,couponParam);
    }

    public List<UserCoupon> findUserCouponByUserId(Long userId) {
        LambdaQueryWrapper<UserCoupon> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserCoupon::getUserId, userId);
        queryWrapper.eq(UserCoupon::getStatus,0);
        List<UserCoupon> userCouponList = userCouponMapper.selectList(queryWrapper);
        return userCouponList;
    }

    public Coupon findCouponById(Long couponId) {
        return this.couponMapper.selectById(couponId);
    }
}

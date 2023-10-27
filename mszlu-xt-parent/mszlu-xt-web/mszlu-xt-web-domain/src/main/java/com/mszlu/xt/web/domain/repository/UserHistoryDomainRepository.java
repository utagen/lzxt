package com.mszlu.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mszlu.xt.pojo.UserHistory;
import com.mszlu.xt.web.dao.UserHistoryMapper;
import com.mszlu.xt.web.domain.UserHistoryDomain;
import com.mszlu.xt.web.model.params.UserHistoryParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserHistoryDomainRepository {
    @Resource
    private UserHistoryMapper userHistoryMapper;


    public UserHistoryDomain createDomain(UserHistoryParam userHistoryParam) {
        return new UserHistoryDomain(this, userHistoryParam);
    }

    public UserHistory findUserHistory(Long userId, Long subjectId, int historyStatus) {
        LambdaQueryWrapper<UserHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHistory::getUserId, userId);
        queryWrapper.eq(UserHistory::getSubjectId, subjectId);
        queryWrapper.eq(UserHistory::getHistoryStatus, historyStatus);
        queryWrapper.last("limit 1");
        return userHistoryMapper.selectOne(queryWrapper);
    }

    public UserHistory findUserHistoryById(Long userId, Long practiceId) {
        LambdaQueryWrapper<UserHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHistory::getUserId, userId);
        queryWrapper.eq(UserHistory::getId, practiceId);
        return userHistoryMapper.selectOne(queryWrapper);
    }

    public void save(UserHistory userHistory) {
        this.userHistoryMapper.insert(userHistory);
    }

    public void updateUserHistoryErrorCount(Long userHistoryId) {
        UpdateWrapper<UserHistory> update = Wrappers.update();
        update.eq("id",userHistoryId);
        update.set("error_count","error_count+1");
        this.userHistoryMapper.update(null, update);
    }

    public void updateUserHistoryStatus(Long historyId, int historyStatus, long finishTime) {
        LambdaUpdateWrapper<UserHistory> update = Wrappers.lambdaUpdate();
        update.eq(UserHistory::getId,historyId);
        update.set(UserHistory::getHistoryStatus,historyStatus);
        update.set(UserHistory::getFinishTime,finishTime);
        this.userHistoryMapper.update(null, update);
    }

    public void updateUserHistoryProgress(Long historyId) {
        UpdateWrapper<UserHistory> update = Wrappers.update();
        update.eq("id",historyId);
        update.set("progress","progress+1");
        this.userHistoryMapper.update(null, update);
    }
}

package com.mszlu.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.pojo.UserHistory;
import com.mszlu.xt.web.dao.UserHistoryMapper;
import com.mszlu.xt.web.domain.UserHistoryDomain;
import com.mszlu.xt.web.model.SubjectModel;
import com.mszlu.xt.web.model.params.UserHistoryParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserHistoryDomainRepository {

    @Resource
    private UserHistoryMapper userHistoryMapper;

    public UserHistoryDomain createDomain(UserHistoryParam userHistoryParam) {
        return new UserHistoryDomain(this,userHistoryParam);
    }

    public UserHistory findUserHistory(Long userId, Long subjectId, int historyStatus) {
        LambdaQueryWrapper<UserHistory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserHistory::getUserId,userId);
        queryWrapper.eq(UserHistory::getSubjectId,subjectId);
        queryWrapper.eq(UserHistory::getHistoryStatus,historyStatus);
        queryWrapper.last("limit 1");
        return userHistoryMapper.selectOne(queryWrapper);
    }

    public UserHistory findUserHistoryById(String id) {
        return userHistoryMapper.selectById(id);
    }

    public void save(UserHistory userHistory) {
        userHistoryMapper.insert(userHistory);
    }

    public void updateUserHistoryStatus(Long historyId, int historyStatus, long finishTime) {
        UserHistory userHistory = new UserHistory();
        userHistory.setId(historyId);
        userHistory.setHistoryStatus(historyStatus);
        userHistory.setFinishTime(finishTime);
        userHistoryMapper.updateById(userHistory);
    }
    //更新错题数量
    public void updateUserHistoryErrorCount(Long userHistoryId) {
        UpdateWrapper<UserHistory> update = Wrappers.update();
        update.eq("id",userHistoryId);
        update.setSql(true,"error_count=error_count+1");
        this.userHistoryMapper.update(null, update);
    }

    public void updateUserHistoryProgress(Long userHistoryId) {
        UpdateWrapper<UserHistory> update = Wrappers.update();
        update.eq("id",userHistoryId);
        //错误的写法
//        update.set("progress","progress+1");
        //正确的写法
        update.setSql(true,"progress=progress+1");
        this.userHistoryMapper.update(null, update);
    }

    public Integer countUserHistoryBySubjectList(Long userId, List<SubjectModel> subjectInfoByCourseId) {
        //计算 课程的学习次数 课程所包含的 学科 学习的次数 ，练习的次数
        List<Long> subjectIdList = subjectInfoByCourseId.stream().map(SubjectModel::getId).collect(Collectors.toList());
        LambdaQueryWrapper<UserHistory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserHistory::getUserId,userId);
        queryWrapper.in(UserHistory::getSubjectId,subjectIdList);
        return this.userHistoryMapper.selectCount(queryWrapper);
    }

    public Page<UserHistory> findUserHistoryList(Long userId, Integer page, Integer pageSize) {
        LambdaQueryWrapper<UserHistory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserHistory::getUserId,userId);
        return userHistoryMapper.selectPage(new Page<>(page,pageSize), queryWrapper);
    }

    public void deletePracticeById(String practiceId) {
        userHistoryMapper.deleteById(practiceId);
    }
}

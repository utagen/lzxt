package com.mszlu.xt.web.domain;

import com.mszlu.xt.pojo.UserHistory;
import com.mszlu.xt.web.domain.repository.UserHistoryDomainRepository;
import com.mszlu.xt.web.model.params.UserHistoryParam;

public class UserHistoryDomain {

    private UserHistoryDomainRepository userHistoryDomainRepository;
    private UserHistoryParam userHistoryParam;

    public UserHistoryDomain(UserHistoryDomainRepository userHistoryDomainRepository, UserHistoryParam userHistoryParam) {
        this.userHistoryDomainRepository = userHistoryDomainRepository;
        this.userHistoryParam = userHistoryParam;
    }

    public UserHistory findUserHistory(Long userId, Long subjectId, int historyStatus) {
        return this.userHistoryDomainRepository.findUserHistory(userId,subjectId,historyStatus);
    }

    public UserHistory findUserHistoryById(Long userId, Long practiceId) {
        return this.userHistoryDomainRepository.findUserHistoryById(userId,practiceId);
    }
    public void saveUserHistory(UserHistory userHistory) {
        userHistoryDomainRepository.save(userHistory);
    }

    public void updateUserHistoryErrorCount(Long userHistoryId) {
        userHistoryDomainRepository.updateUserHistoryErrorCount(userHistoryId);
    }

    public void updateUserHistoryStatus(Long historyId, int historyStatus, long finishTime) {
        userHistoryDomainRepository.updateUserHistoryStatus(historyId,historyStatus,finishTime);
    }

    public void updateUserHistoryProgress(Long historyId) {
        userHistoryDomainRepository.updateUserHistoryProgress(historyId);
    }

}
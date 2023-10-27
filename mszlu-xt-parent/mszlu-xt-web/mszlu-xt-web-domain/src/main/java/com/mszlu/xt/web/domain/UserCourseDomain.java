package com.mszlu.xt.web.domain;

import com.mszlu.xt.pojo.UserCourse;
import com.mszlu.xt.web.domain.repository.UserCourseDomainRepository;
import com.mszlu.xt.web.model.params.UserCourseParam;

import java.util.List;

public class UserCourseDomain {
    private UserCourseDomainRepository userCourseDomainRepository;
    private UserCourseParam userCourseParam;
    public UserCourseDomain(UserCourseDomainRepository userCourseDomainRepository, UserCourseParam userCourseParam) {
        this.userCourseDomainRepository = userCourseDomainRepository;
        this.userCourseParam = userCourseParam;
    }

    public UserCourse findUserCourse(Long userId, Long courseId,long currentTime) {
        return userCourseDomainRepository.findUserCourse(userId,courseId,currentTime);
    }

    public long countUserCourseByCourseId(Long courseId) {
        return userCourseDomainRepository.countUserCourseByCourseId(courseId);
    }

    public Integer countUserCourse(Long courseId) {
        return userCourseDomainRepository.countUserCourse(courseId);
    }

    public UserCourse findUserCourseByUserIdAndCourseId() {
        Long courseId = userCourseParam.getCourseId();
        Long userId = userCourseParam.getUserId();
        Long time = userCourseParam.getTime();
        return userCourseDomainRepository.findUserCourseByUserIdAndCourseId(courseId,userId,time);
    }

    public Integer countUserCourseByUserId(Long userId,
                                           List<Long> courseIdList,
                                           long currentTime) {
        return userCourseDomainRepository.countUserCourseByUserId(userId,courseIdList,currentTime);
    }
}
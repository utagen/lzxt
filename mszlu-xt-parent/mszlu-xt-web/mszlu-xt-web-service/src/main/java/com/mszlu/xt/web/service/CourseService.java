package com.mszlu.xt.web.service;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.CourseParam;

public interface CourseService {
    /**
     * 进入学习 课程列表
     * @param courseParam
     * @return
     */
    CallResult courseList(CourseParam courseParam);

    /**
     * 根据课程id查询学科详细信息
     * @param courseParam
     * @return
     */
    CallResult subjectInfo(CourseParam courseParam);

    /**
     * 查询课程详情
     * @param courseParam
     * @return
     */
    CallResult courseDetail(CourseParam courseParam);

    CallResult myCoupon(CourseParam courseParam);

    /**
     * 查询我购买的课程列表
     * @param courseParam
     * @return
     */
    CallResult myCourse(CourseParam courseParam);
}

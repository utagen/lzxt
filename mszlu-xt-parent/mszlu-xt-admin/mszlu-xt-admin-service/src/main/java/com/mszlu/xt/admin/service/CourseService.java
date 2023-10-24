package com.mszlu.xt.admin.service;


import com.mszlu.xt.admin.params.CourseParam;
import com.mszlu.xt.common.model.CallResult;

public interface CourseService {
    CallResult saveCourse(CourseParam courseParam);

    CallResult updateCourse(CourseParam courseParam);

    CallResult findCourseById(CourseParam courseParam);

    CallResult findPage(CourseParam courseParam);
}
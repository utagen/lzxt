package com.mszlu.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.xt.pojo.Course;
import com.mszlu.xt.pojo.CourseSubject;
import com.mszlu.xt.pojo.SubjectUnit;
import com.mszlu.xt.web.dao.CourseMapper;
import com.mszlu.xt.web.dao.CourseSubjectMapper;
import com.mszlu.xt.web.domain.CourseDomain;
import com.mszlu.xt.web.domain.SubjectDomain;
import com.mszlu.xt.web.domain.UserCourseDomain;
import com.mszlu.xt.web.domain.UserHistoryDomain;
import com.mszlu.xt.web.model.params.CourseParam;
import com.mszlu.xt.web.model.params.SubjectParam;
import com.mszlu.xt.web.model.params.UserCourseParam;
import com.mszlu.xt.web.model.params.UserHistoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseDomainRepository {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseSubjectMapper courseSubjectMapper;
    @Autowired
    private UserCourseDomainRepository userCourseDomainRepository;
    @Autowired
    private SubjectDomainRepository subjectDomainRepository;
    @Autowired
    private UserHistoryDomainRepository userHistoryDomainRepository;

    public CourseDomain createDomain(CourseParam courseParam) {
        return new CourseDomain(this, courseParam);
    }

    public Page<Course> findCourseByGrade(int currentPage,
                                          int pageSize,
                                          String subjectGrade) {
        //select * from t_course where course_status = 0 and id in (select course_id from t_course_subject where subject_id in ( select id from t_subject where subject_grade='七年级') group by course_id)
        Page<Course> page = new Page<>(currentPage, pageSize);
        return courseMapper.findCourseByGrade(page, subjectGrade);
    }

    public Page<Course> findAllCourse(int currentPage,
                                      int pageSize) {
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseStatus, 0);
        Page<Course> page = new Page<>(currentPage, pageSize);
        Page<Course> coursePage = courseMapper.selectPage(page, queryWrapper);
        return coursePage;
    }

    public UserCourseDomain createUserCourseDomain(UserCourseParam userCourseParam) {
        return this.userCourseDomainRepository.createDomain(userCourseParam);
    }

    public SubjectDomain createSubjectDomain(SubjectParam subjectParam) {
        return this.subjectDomainRepository.createDomain(subjectParam);
    }

    public Course findCourseById(Long courseId) {
        return courseMapper.selectById(courseId);
    }

    public List<Long> findCourseIdBySubject(Long subjectId) {
        LambdaQueryWrapper<CourseSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseSubject::getSubjectId, subjectId);
        queryWrapper.select(CourseSubject::getCourseId);
        List<CourseSubject> courseSubjects = courseSubjectMapper.selectList(queryWrapper);
        return courseSubjects.stream().map(CourseSubject::getCourseId).collect(Collectors.toList());
    }


    public UserHistoryDomain createUserHisToryDomain(UserHistoryParam userHistoryParam) {
        return userHistoryDomainRepository.createDomain(userHistoryParam);
    }
}
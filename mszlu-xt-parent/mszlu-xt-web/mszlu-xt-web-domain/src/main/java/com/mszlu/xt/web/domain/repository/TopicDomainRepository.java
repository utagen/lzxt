package com.mszlu.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.xt.pojo.Topic;
import com.mszlu.xt.pojo.UserPractice;
import com.mszlu.xt.web.dao.TopicMapper;
import com.mszlu.xt.web.dao.data.TopicDTO;
import com.mszlu.xt.web.domain.*;
import com.mszlu.xt.web.model.params.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TopicDomainRepository {

    @Resource
    private TopicMapper topicMapper;

    @Autowired
    private CourseDomainRepository courseDomainRepository;
    @Autowired
    private UserCourseDomainRepository userCourseDomainRepository;
    @Autowired
    private UserHistoryDomainRepository userHistoryDomainRepository;
    @Autowired
    private UserPracticeDomainRepository userPracticeDomainRepository;
    @Autowired
    private UserProblemDomainRepository userProblemDomainRepository;
    @Autowired
    private SubjectDomainRepository subjectDomainRepository;

    public TopicDomain createDomain(TopicParam topicParam) {
        return new TopicDomain(this,topicParam);
    }

    public CourseDomain getCourseDomain(CourseParam courseParam) {
        return courseDomainRepository.createDomain(courseParam);
    }

    public UserCourseDomain getUserCourseDomain(UserCourseParam userCourseParam) {
        return userCourseDomainRepository.createDomain(userCourseParam);
    }

    public UserHistoryDomain createHistoryDomain(UserHistoryParam userHistoryParam) {
        return userHistoryDomainRepository.createDomain(userHistoryParam);
    }

    public List<Long> findTopicRandomList(Long subjectId,
                                          String topicAreaPro,
                                          List<Integer> subjectUnitList) {

        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getTopicSubject,subjectId);
        if (StringUtils.isNotBlank(topicAreaPro)) {
            queryWrapper.likeRight(Topic::getTopicAreaPro, topicAreaPro);
        }
        if (CollectionUtils.isNotEmpty(subjectUnitList)) {
            queryWrapper.in(Topic::getSubjectUnit, subjectUnitList);
        }
        queryWrapper.last("order by RAND() LIMIT 50");
        Object object = topicMapper.selectObjs(queryWrapper);
        return (List<Long>) object;
    }

    public UserPracticeDomain createUserPracticeDomain(UserPracticeParam userPracticeParam) {
        return userPracticeDomainRepository.createDomain(userPracticeParam);
    }

   public TopicDTO findTopicAnswer(Long userId, Long topicId, Long userHistoryId) {
        UserPractice userPractice = this.userPracticeDomainRepository.createDomain(null).findUserPracticeByTopicId(userId,topicId,userHistoryId);
        Topic topic = topicMapper.selectById(topicId);
        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic,topicDTO);
        topicDTO.setPStatus(userPractice.getPStatus());
        topicDTO.setUserAnswer(userPractice.getUserAnswer());
        return topicDTO;
    }

    public SubjectDomain createSubjectDomain(SubjectParam subjectParam) {
        return subjectDomainRepository.createDomain(subjectParam);
    }

    public Topic findTopicById(Long topicId) {
        return topicMapper.selectById(topicId);
    }

    public UserHistoryDomain createUserHistoryDomain(UserHistoryParam userHistoryParam) {
        return userHistoryDomainRepository.createDomain(userHistoryParam);
    }

    public UserProblemDomain createUserProblemDomain(UserProblemParam userProblemParam) {
        return userProblemDomainRepository.createDomain(userProblemParam);
    }
}
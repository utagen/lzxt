package com.mszlu.xt.admin.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.xt.admin.dao.TopicMapper;
import com.mszlu.xt.admin.domain.SubjectDomain;
import com.mszlu.xt.admin.domain.TopicDomain;
import com.mszlu.xt.admin.params.SubjectParam;
import com.mszlu.xt.admin.params.TopicParam;
import com.mszlu.xt.pojo.Subject;
import com.mszlu.xt.pojo.Topic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jarno
 */
@Component
public class TopicDomainRepository {

    @Resource
    private TopicMapper topicMapper;
    @Autowired
    private SubjectDomainRepository subjectDomainRepository;

    public TopicDomain createDomain(TopicParam topicParam) {
        return new TopicDomain(topicParam,this);
    }


    public Page<Topic> findPage(int currentPage, int size, String topicTitle, Long subjectId) {
        Page<Topic> page = new Page<>(currentPage,size);
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(topicTitle)) {
            queryWrapper.like(Topic::getTopicTitle, topicTitle);
        }
        if (subjectId != null){
            queryWrapper.eq(Topic::getTopicSubject,subjectId);
        }
        return topicMapper.selectPage(page, queryWrapper);
    }

    public SubjectDomain createSubjectDomain(SubjectParam subjectParam) {
        return subjectDomainRepository.createDomain(subjectParam);
    }
}
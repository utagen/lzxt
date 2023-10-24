package com.mszlu.xt.admin.service.impl;

import com.mszlu.xt.admin.domain.TopicDomain;
import com.mszlu.xt.admin.domain.repository.TopicDomainRepository;
import com.mszlu.xt.admin.params.TopicParam;
import com.mszlu.xt.admin.service.TopicService;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.common.service.AbstractTemplateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends AbstractService implements TopicService {
    @Autowired
    private TopicDomainRepository topicDomainRepository;
    @Override
    public CallResult findTopicList(TopicParam topicParam) {
        TopicDomain topicDomain = this.topicDomainRepository.createDomain(topicParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return topicDomain.findTopicList();
            }
        });
    }
}

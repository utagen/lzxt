package com.mszlu.xt.admin.service;

import com.mszlu.xt.admin.params.TopicParam;
import com.mszlu.xt.common.model.CallResult;

public interface TopicService {
    /**
     * 根据条件进行对应的题目 分页查询
     * @param topicParam
     * @return
     */
    CallResult findTopicList(TopicParam topicParam);
}

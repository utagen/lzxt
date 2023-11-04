package com.mszlu.xt.web.service;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.TopicParam;

public interface TopicService {
    /**
     * 开始学习
     * @param topicParam
     * @return
     */
    CallResult practice(TopicParam topicParam);

    CallResult submit(TopicParam topicParam);

    CallResult jump(TopicParam topicParam);

    /**
     * 我的学习
     * @param topicParam
     * @return
     */
    CallResult practiceHistory(TopicParam topicParam);

    /**
     * 错题本
     * @param topicParam
     * @return
     */
    CallResult userProblemSearch(TopicParam topicParam);

    /**
     * 取消本次练习
     * @param topicParam
     * @return
     */
    CallResult cancel(TopicParam topicParam);
}
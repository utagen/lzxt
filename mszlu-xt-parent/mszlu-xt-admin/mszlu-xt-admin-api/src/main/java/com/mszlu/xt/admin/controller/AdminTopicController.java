package com.mszlu.xt.admin.controller;


import com.mszlu.xt.admin.params.TopicParam;
import com.mszlu.xt.admin.service.TopicService;
import com.mszlu.xt.common.model.CallResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jarno
 */
@RestController
@RequestMapping("topic")
@Slf4j
public class AdminTopicController {

    @Autowired
    private TopicService topicService;


    @RequestMapping(value = "findPage")
    public CallResult findPage(@RequestBody TopicParam topicParam){
        return topicService.findTopicList(topicParam);
    }

}

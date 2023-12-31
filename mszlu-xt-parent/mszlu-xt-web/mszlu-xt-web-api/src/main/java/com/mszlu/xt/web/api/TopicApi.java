package com.mszlu.xt.web.api;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.TopicParam;
import com.mszlu.xt.web.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("topic")
public class TopicApi {

    @Autowired
    private TopicService topicService;



    @RequestMapping(value = "practice",method = RequestMethod.POST)
    public CallResult practice(@RequestBody TopicParam topicParam){
        return topicService.practice(topicParam);
    }

    @PostMapping("submit")
    public CallResult submit(@RequestBody TopicParam topicParam){
        return topicService.submit(topicParam);
    }

    @PostMapping("jump")
    public CallResult jump(@RequestBody TopicParam topicParam){
        return topicService.jump(topicParam);
    }

    @PostMapping(value = "practiceHistory")
    public CallResult practiceHistory(@RequestBody TopicParam topicParam){
        return topicService.practiceHistory(topicParam);
    }

    @PostMapping(value = "userProblemSearch")
    public CallResult userProblemSearch(@RequestBody TopicParam topicParam){
        return topicService.userProblemSearch(topicParam);
    }

    @PostMapping("cancel")
    public CallResult cancel(@RequestBody TopicParam topicParam) {
        return topicService.cancel(topicParam);
    }
}

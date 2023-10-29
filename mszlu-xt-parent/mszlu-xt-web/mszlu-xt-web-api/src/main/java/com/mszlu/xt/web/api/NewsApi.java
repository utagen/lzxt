package com.mszlu.xt.web.api;


import com.mszlu.xt.common.cache.Cache;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.NewsParam;
import com.mszlu.xt.web.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
@Slf4j
public class NewsApi {

    @Autowired
    private NewsService newsService;

    @PostMapping("newsList")
    @Cache(name = "newsList", time = 2 * 60)
    public CallResult newsList(@RequestBody NewsParam newsParam){
//        log.info("新闻管理列表访问参数:{}", newsParam);
        return newsService.newsList(newsParam);
    }

    @PostMapping("detail")
    @Cache(name = "newsDetail", time = 30)
    public CallResult news(@RequestBody NewsParam newsParam){
        return newsService.findNewsById(newsParam);
    }

//    @PostMapping("newsDetailList")
//    public CallResult newsDetailList(@RequestBody NewsParam newsParam){
//        return newsService.newsDetailList(newsParam);
//    }
}
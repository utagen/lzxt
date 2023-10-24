package com.mszlu.xt.admin.controller;

import com.mszlu.xt.admin.params.NewsParam;
import com.mszlu.xt.admin.service.NewsService;
import com.mszlu.xt.common.model.CallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jarno
 */
@RestController
@RequestMapping("news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping(value = "save")
    public CallResult save(@RequestBody NewsParam newsParam){
        return newsService.save(newsParam);
    }

    @PostMapping(value = "update")
    public CallResult update(@RequestBody NewsParam newsParam){
        return newsService.update(newsParam);
    }

    @PostMapping(value = "findNewsById")
    public CallResult findNewsById(@RequestBody NewsParam newsParam){
        return newsService.findNewsById(newsParam);
    }

    @PostMapping(value = "findPage")
    public CallResult findPage(@RequestBody NewsParam newsParam){
        return newsService.findPage(newsParam);
    }

    @PostMapping(value = "upload")
    public CallResult upload(@RequestParam("imageFile") MultipartFile file){
        //图片往七牛云上传
        System.out.println("进入了上传图片");
        return newsService.upload(file);
    }
}

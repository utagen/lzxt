package com.mszlu.xt.admin.config;

import com.mszlu.xt.admin.template.StringTemplate;
import com.mszlu.xt.admin.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    StringTemplate stringTemplate;
    @Autowired
    private freemarker.template.Configuration configuration;


    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("strstr", stringTemplate);
    }

}
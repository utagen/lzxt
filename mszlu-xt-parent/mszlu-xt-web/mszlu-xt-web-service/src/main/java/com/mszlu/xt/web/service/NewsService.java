package com.mszlu.xt.web.service;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.NewsParam;

public interface NewsService {

    /**
     * 分页查询 新闻列表
     * @param newsParam
     * @return
     */
    CallResult newsList(NewsParam newsParam);

    /**
     *
     * @param newsParam
     * @return
     */
    CallResult findNewsById(NewsParam newsParam);
}

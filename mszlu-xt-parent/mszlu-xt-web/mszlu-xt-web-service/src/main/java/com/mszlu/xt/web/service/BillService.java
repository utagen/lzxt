package com.mszlu.xt.web.service;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.BillParam;

public interface BillService {
    /**
     * 查询所有的海报列表
     * @param billParam
     * @return
     */
    CallResult all(BillParam billParam);

    /**
     * 生成推广链接
     * @param billParam
     * @return
     */
    CallResult gen(BillParam billParam);
}

package com.mszlu.xt.web.model.params;

import lombok.Data;

@Data
public class UserCourseParam {
    private Long id;
    private Long userId;
    private Long courseId;
    private Long createTime;
    private Long expireTime;
    private Integer studyCount;

    public Long getTime() {
        return expireTime - createTime;
    }
}

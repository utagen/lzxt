package com.mszlu.xt.web.model;


import com.mszlu.xt.common.model.topic.FillBlankChoice;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Jarno
 */
@Data
public class TopicModel {

    private String id;

    private String addAdmin;

    private String topicTitle;

    private String topicTypeStr;

    private Integer topicType;

    private String topicImg;

    private String topicChoice;

    private Integer topicStar;

    private String topicAreaPro;

    private String topicAreaCity;

    private List<FillBlankChoice> fillBlankChoiceList;

    private int fillBlankTopicChoice;

    private List<Map<String,String>> radioChoice;
    private List<Map<String,String>> mulChoice;

    private String answer;

    private String analyze;

    private String subjectStr;

    private Long subject;

    private String createTime;

    private String lastUpdateTime;
}

package com.mszlu.xt.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Jarno
 */
@Data
public class Subject {
    private Long id;
    private String subjectName;
    private String subjectGrade;
    private String subjectTerm;
    private Integer status;
}
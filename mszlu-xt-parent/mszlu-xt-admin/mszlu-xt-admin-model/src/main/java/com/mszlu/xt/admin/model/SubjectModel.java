package com.mszlu.xt.admin.model;

import lombok.Data;

import java.util.List;

/**
 * @author Jarno
 */
@Data
public class SubjectModel {
    private Long id;
    private Long numberId;
    private String subjectName;
    private String subjectGrade;
    private String subjectTerm;
    private Integer status;

    private List<Integer> subjectUnits;

    public void fillSubjectName() {
        this.subjectName = this.subjectName + "-" + this.subjectGrade + "-" + subjectTerm;
    }
}
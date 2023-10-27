package com.mszlu.xt.web.model;

import lombok.Data;

import java.util.List;

/**
 * @author Jarno
 */
@Data
public class SubjectViewModel {
    private Long id;
    private Long numberId;
    private String subjectName;
    private String subjectGrade;
    private String subjectTerm;

    private List<Integer> subjectUnitList;

    private List<Integer> subjectUnitSelectedList;

    public void fillSubjectName() {
        this.subjectName = this.subjectName + "-" + this.subjectGrade + "-" + subjectTerm;
    }
}
package com.mszlu.xt.admin.controller;


import com.mszlu.xt.admin.params.SubjectParam;
import com.mszlu.xt.admin.service.SubjectService;
import com.mszlu.xt.common.model.CallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jarno
 */
@RestController
@RequestMapping("subject")
public class AdminSubjectController {

    @Autowired
    private SubjectService subjectService;


    @PostMapping(value = "findPage")
    public CallResult findPage(@RequestBody SubjectParam subjectParam){
        return  subjectService.findSubjectList(subjectParam);
    }

    @PostMapping(value = "saveSubject")
    public CallResult saveSubject(@RequestBody SubjectParam subjectParam){
        return  subjectService.saveSubject(subjectParam);
    }

    @PostMapping(value = "findSubjectById")
    public CallResult findSubjectById(@RequestBody SubjectParam subjectParam){
        return  subjectService.findSubjectById(subjectParam);
    }

    @PostMapping(value = "updateSubject")
    public CallResult updateSubject(@RequestBody SubjectParam subjectParam){
        return  subjectService.updateSubject(subjectParam);
    }

    @PostMapping(value = "allSubjectList")
    public CallResult allSubjectList(@RequestBody SubjectParam subjectParam){
        return  subjectService.allSubjectList(subjectParam);
    }
}

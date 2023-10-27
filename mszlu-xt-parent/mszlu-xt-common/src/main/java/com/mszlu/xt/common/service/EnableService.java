package com.mszlu.xt.common.service;

import com.mszlu.xt.common.service.ServiceTemplateImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServiceTemplateImpl.class)
public @interface EnableService {
}
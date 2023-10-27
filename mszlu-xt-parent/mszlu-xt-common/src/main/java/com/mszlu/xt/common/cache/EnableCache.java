package com.mszlu.xt.common.cache;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheAspect.class)
public @interface EnableCache {
}
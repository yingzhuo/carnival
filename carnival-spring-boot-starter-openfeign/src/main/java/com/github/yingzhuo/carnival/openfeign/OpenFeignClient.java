package com.github.yingzhuo.carnival.openfeign;

import com.github.yingzhuo.carnival.openfeign.target.BrokenUrlSupplier;
import com.github.yingzhuo.carnival.openfeign.target.UrlSupplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OpenFeignClient {

    public String url() default "<:::NO VALUE:::>";

    public Class<? extends UrlSupplier> urlSupplier() default BrokenUrlSupplier.class;

    public boolean primary() default true;

}

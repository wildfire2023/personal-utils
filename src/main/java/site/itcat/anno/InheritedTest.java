package site.itcat.anno;

import java.lang.annotation.*;

/**
 * author : wildfire
 * date : 2021/12/27 22:53
 * description : @Inherited annotation marked at super class , the subclass can inherit the outer annotation
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InheritedTest {
    String value() default "";
}

package ru.relex.budget.services.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Documented
@Component
@Validated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Facade {
}

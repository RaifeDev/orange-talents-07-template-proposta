package com.zup.propostas.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniquePropostaValidator.class)
public @interface UniqueProposta {
    String message() default "Duplicidade no sistema";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String value() default "";
    String fieldName();
    Class<?> domainClass();

}

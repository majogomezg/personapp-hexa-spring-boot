package co.edu.javeriana.as.personapp.common.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface Adapter {
	
	// Solo un alias para abstraernos de Spring framework
    @AliasFor(annotation = Component.class)
    String value() default "";

}

package group10.doodling.util.annotation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Schema(hidden = true)
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {

}

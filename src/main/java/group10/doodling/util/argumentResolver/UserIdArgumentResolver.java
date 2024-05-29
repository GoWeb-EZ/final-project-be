package group10.doodling.util.argumentResolver;

import group10.doodling.component.TokenManager;
import group10.doodling.util.annotation.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenManager tokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
       boolean hasUserIdAnnotation = parameter.hasParameterAnnotation(UserId.class);
       boolean hasStringType = String.class.isAssignableFrom(parameter.getParameterType());

       return hasStringType && hasUserIdAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String authorizationField = httpRequest.getHeader("Authorization");

        // 토큰을 파싱하여 사용자 ID 추출
        String token = getTokenArea(authorizationField);

        //커스텀 어노테이션(@UserId)이 적용된 파라미터에 사용자 ID 주입
        if (token != null) {
            Jws<Claims> claimsJws = tokenManager.verifyToken(token);
            return claimsJws.getPayload().get("user_id");
        }
        return null;
    }

    private String getTokenArea(String authorizationField) {
        // 토큰 파싱 로직을 구현하여 사용자 ID를 추출
        // 예시로 토큰이 "Bearer <사용자 ID>" 형식일 때만 사용자 ID를 추출
        if (authorizationField != null && authorizationField.startsWith("Bearer ")) {
            return authorizationField.substring(7); // "Bearer " 다음의 문자열이 사용자 ID
        }
        return null; // 토큰이 없거나 형식이 잘못된 경우 null 반환
    }
}

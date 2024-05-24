package group10.doodling.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import group10.doodling.component.TokenManager;
import group10.doodling.controller.dto.common.ErrorResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order()
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private static final String[] whiteList = {"/api/auth/login-url", "/api/auth/login/oauth2/callback"};
    private final TokenManager tokenManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String token = getToken(httpRequest);
        boolean isWhiteList = Arrays.asList(whiteList).contains(requestURI);

        try {

            if (isWhiteList) {
                filterChain.doFilter(request, response);
            }

            if (isValidToken(token)) {
                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("utf-8");
            if (e instanceof ExpiredJwtException) {
                httpResponse.getWriter().write(objectMapper.writeValueAsString(new ErrorResult(
                        HttpStatus.UNAUTHORIZED,
                        "만료된 토큰입니다."
                )));
            }
            else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException) {
                httpResponse.getWriter().write(objectMapper.writeValueAsString(new ErrorResult(
                        HttpStatus.UNAUTHORIZED,
                        "잘못된 토큰입니다."
                )));
            }
            else {
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpResponse.getWriter().write(objectMapper.writeValueAsString(new ErrorResult(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error"
                )));
            }
        }

    }

    private String getToken(HttpServletRequest request) {
        String authorizationField = request.getHeader("Authorization");
        if (authorizationField != null && authorizationField.startsWith("Bearer ")) {
            return authorizationField.substring(7); // "Bearer " 다음의 문자열이 사용자 ID
        }
        return null;
    }

    private boolean isValidToken(String token) {
        return tokenManager.verifyToken(token) != null;
    }

}

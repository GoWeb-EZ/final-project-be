package group10.doodling.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import group10.doodling.component.TokenManager;
import group10.doodling.controller.dto.common.ErrorResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whiteList = {
            "/",
            "/api-docs",
            "/api/auth/login-url",
            "/api/auth/login/oauth2/callback",
            "/api/auth/verify",
            "/api/test-exception",
            "/api/test-mongodb-user",
            "/api/test-upload",
            "/api/image",
            "/api/test-create-note",
            "/code",
            "/api/test-update-note",
            "/api/test-delete-note",
            "/api/test-preview-note",
            "/api/test-detail-note",
            "/api/test-search-tag"
    };
    private final TokenManager tokenManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String token = getToken(request);
        boolean isWhiteList = Arrays.asList(whiteList).contains(requestURI) || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3");

        if (request.getMethod().equals("OPTIONS")) { // Preflight 요청 시 동작
            filterChain.doFilter(request, response);
            return;
        }

        if (isWhiteList) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (isValidToken(token)) {
                logger.info("Token of the user accessed: " + token);
            } else {
                throw new RuntimeException();
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private String getToken(HttpServletRequest request) {
        String authorizationField = request.getHeader("Authorization");
        if (authorizationField != null && authorizationField.startsWith("Bearer ")) {
            return authorizationField.substring(7); // "Bearer " 다음의 문자열이 토큰
        }
        return null;
    }

    private boolean isValidToken(String token) {
        return token != null && tokenManager.verifyToken(token) != null;
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorResult errorResult = new ErrorResult(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다.");
        if (e instanceof ExpiredJwtException) {
            errorResult.setMessage("만료된 토큰입니다.");
        } else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException) {
            errorResult.setMessage("잘못된 토큰입니다.");
        }
        response.getWriter().write(objectMapper.writeValueAsString(errorResult));
    }
}

package group10.doodling.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import group10.doodling.component.KakaoWorkBotManager;
import group10.doodling.controller.dto.common.ErrorResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final KakaoWorkBotManager kakaoWorkBotManager;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        kakaoWorkBotManager.sendErrorMessageToKakaoWorkAPI(request.getMethod(), request.getRequestURI(), ex);
        ex.printStackTrace();

        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return ResponseEntity.internalServerError().body(errorResult);
    }

}

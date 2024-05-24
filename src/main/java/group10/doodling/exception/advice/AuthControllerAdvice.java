package group10.doodling.exception.advice;

import group10.doodling.controller.dto.common.ErrorResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResult expiredJwtExceptionHandle(ExpiredJwtException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult(HttpStatus.UNAUTHORIZED, "토큰 시간이 만료되었습니다.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedJwtException.class)
    public ErrorResult unsupportedJwtExceptionHandle(UnsupportedJwtException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰입니다.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ErrorResult malformedJwtExceptionHandle(MalformedJwtException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult(HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult internalServerErrorHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}

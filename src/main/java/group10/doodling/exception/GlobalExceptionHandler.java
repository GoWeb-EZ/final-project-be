package group10.doodling.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.doodling.controller.dto.common.ErrorResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${errorbot.kakao-work.key}")
    private String KAKAO_WORK_BOT_KEY;

    @Value("${errorbot.kakao-work.room-id}")
    private String KAKAO_WORK_ROOM_ID;

    private final String KAKAO_WORK_BOT_ENDPOINT =  "https://api.kakaowork.com/v1/messages.send";

    private final RestTemplate restTemplate = new RestTemplate();


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        sendKErrorMessageToKakaoWorkAPI(request.getMethod(), request.getRequestURI(), ex);
        ex.printStackTrace();

        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return ResponseEntity.internalServerError().body(errorResult);
    }


    private void sendKErrorMessageToKakaoWorkAPI(String method, String uri, Exception ex) throws JsonProcessingException {
        HttpHeaders headers = createAuthorizationHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String stacktrace = ExceptionUtils.getStackTrace(ex);
        String message = "URI: ["+ method + " " + uri + "]\n" + "Invoked: " + (new Date()).toString() + "\n" + stacktrace;
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("conversation_id", KAKAO_WORK_ROOM_ID);
        jsonBody.put("text", message);


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonBody);

        ResponseEntity<String> userInfoResponse = restTemplate.postForEntity(KAKAO_WORK_BOT_ENDPOINT, new HttpEntity<>(jsonString, headers), String.class);
    }

    HttpHeaders createAuthorizationHeader(){
        return new HttpHeaders() {{
            set( "Authorization", "Bearer " + KAKAO_WORK_BOT_KEY );
        }};
    }
}

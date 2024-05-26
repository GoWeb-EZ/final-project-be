package group10.doodling.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${errorbot.kakao-work.key}")
    private String KAKAO_WORK_BOT_KEY;

    @Value("${errorbot.kakao-work.room-id}")
    private String KAKAO_WORK_ROOM_ID;

    private final String KAKAO_WORK_BOT_ENDPOINT =  "https://api.kakaowork.com/v1/messages.send";

    private final RestTemplate restTemplate = new RestTemplate();


    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex) {
        sendKErrorMessageToKakaoWorkAPI(ex.getMessage());
    }


    private void sendKErrorMessageToKakaoWorkAPI(String message) {
        HttpHeaders headers = createAuthorizationHeader();
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();

        body.add("conversation_id", KAKAO_WORK_ROOM_ID);
        body.add("text", message);

        ResponseEntity<String> userInfoResponse = restTemplate.postForEntity(KAKAO_WORK_BOT_ENDPOINT, new HttpEntity<>(body, headers), String.class);
    }

    HttpHeaders createAuthorizationHeader(){
        return new HttpHeaders() {{
            set( "Authorization", "Bearer " + KAKAO_WORK_BOT_KEY );
        }};
    }
}

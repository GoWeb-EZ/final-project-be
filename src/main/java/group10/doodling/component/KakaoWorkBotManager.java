package group10.doodling.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoWorkBotManager {

    @Value("${errorbot.kakao-work.key}")
    private String KAKAO_WORK_BOT_KEY;

    @Value("${errorbot.kakao-work.room-id}")
    private String KAKAO_WORK_ROOM_ID;

    private final String KAKAO_WORK_BOT_ENDPOINT =  "https://api.kakaowork.com/v1/messages.send";

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendErrorMessageToKakaoWorkAPI(String method, String uri, Exception ex) throws JsonProcessingException {
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

    private HttpHeaders createAuthorizationHeader(){
        return new HttpHeaders() {{
            set( "Authorization", "Bearer " + KAKAO_WORK_BOT_KEY );
        }};
    }
}

package group10.doodling.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.doodling.controller.dto.request.auth.OauthUserInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OAuthManager {

    @Value("${oauth.kakao-client-key}")
    private String KAKAO_CLIENT_KEY;

    @Value("${oauth.kakao-client-secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${host-address}")
    private String HOST_ADDRESS;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public enum OauthType {
        KAKAO
    }

    public String createRedirectURI(OauthType oauthType) {
        if (oauthType.equals(OauthType.KAKAO))
            return createKakaoRedirectURI();
        return null;
    }

    private String createKakaoRedirectURI() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+KAKAO_CLIENT_KEY+"&redirect_uri=http://"+HOST_ADDRESS+":8080/";
    }

    public String getAccessToken(OauthType oauthType, String code, String redirectURI) throws JsonProcessingException {
        if (oauthType.equals(OauthType.KAKAO))
            return getKakaoOauthAccessToken(code, redirectURI);
        return null;
    }

    private String getKakaoOauthAccessToken(String code, String redirectURI) throws JsonProcessingException {

        String kakaoOauthTokenEndPoint = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();

        body.add("grant_type","authorization_code");
        body.add("client_id", KAKAO_CLIENT_KEY);
        body.add("redirect_uri",redirectURI);
        body.add("code",code);


        ResponseEntity<String> oauthTokenResponse = restTemplate.postForEntity(kakaoOauthTokenEndPoint, new HttpEntity<>(body, headers), String.class);

        JsonNode tokenResponseToJsonNode = objectMapper.readTree(oauthTokenResponse.getBody());
        return tokenResponseToJsonNode.get("access_token").asText();
    }

    public OauthUserInfoResponseDTO getUserInfoByOauthAPI(OauthType oauthType, String accessToken) throws JsonProcessingException {
        if (oauthType.equals(OauthType.KAKAO))
            return getUserInfoByKakaoOauthAPI(accessToken);
        return null;
    }

    private OauthUserInfoResponseDTO getUserInfoByKakaoOauthAPI(String accessToken) throws JsonProcessingException {

        String kakaoOauthUserInfoAPIEndpoint = "https://kapi.kakao.com/v2/user/me";

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(kakaoOauthUserInfoAPIEndpoint, HttpMethod.GET, new HttpEntity<>(createAuthorizationHeader(accessToken)), String.class);

        JsonNode jsonNode = objectMapper.readTree(userInfoResponse.getBody());
        OauthUserInfoResponseDTO oauthUserInfoResponseDTO = new OauthUserInfoResponseDTO();
        oauthUserInfoResponseDTO.setOauthId(jsonNode.get("id").asLong());
        oauthUserInfoResponseDTO.setUsername(jsonNode.get("properties").get("nickname").asText());

        return oauthUserInfoResponseDTO;
    }

    private HttpHeaders createAuthorizationHeader(String token){
        return new HttpHeaders() {{
            set( "Authorization", "Bearer " + token );
        }};
    }
}

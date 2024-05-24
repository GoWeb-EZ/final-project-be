package group10.doodling.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group10.doodling.component.TokenManager;
import group10.doodling.controller.dto.request.auth.OauthUserInfoResponseDTO;
import group10.doodling.controller.dto.response.auth.LoginURLResponseDTO;
import group10.doodling.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenManager tokenManager;

    @GetMapping("/login-url")
    public ResponseEntity<LoginURLResponseDTO> getLoginURL() {  // 미구현
        String loginURL = authService.createRedirectURI(AuthService.OauthType.KAKAO);

        LoginURLResponseDTO loginURLResponseDTO = new LoginURLResponseDTO();
        loginURLResponseDTO.setLoginURL(loginURL);
        return ResponseEntity.ok().body(loginURLResponseDTO);
    }

    @GetMapping("/login/oauth2/callback")
    public ResponseEntity<String> login(@RequestParam String code) throws JsonProcessingException {

        String accessToken = authService.getAccessToken(AuthService.OauthType.KAKAO, code);
        OauthUserInfoResponseDTO oauthUserInfoResponseDTO = authService.getUserInfoByOauthAPI(AuthService.OauthType.KAKAO, accessToken);

        String userId = authService.login(oauthUserInfoResponseDTO.getOauthId(), oauthUserInfoResponseDTO.getUsername());

        String token = tokenManager.generateToken(userId, oauthUserInfoResponseDTO.getUsername());

        return ResponseEntity.ok().body(token);
    }



}

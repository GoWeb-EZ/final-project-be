package group10.doodling.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group10.doodling.component.TokenManager;
import group10.doodling.controller.dto.request.auth.OauthUserInfoResponseDTO;
import group10.doodling.controller.dto.request.auth.VerifyTokenRequestDTO;
import group10.doodling.controller.dto.response.auth.LoginURLResponseDTO;
import group10.doodling.controller.dto.response.auth.TokenResponseDTO;
import group10.doodling.controller.dto.response.auth.VerifyTokenResponseDTO;
import group10.doodling.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenManager tokenManager;

    @GetMapping("/login-url")
    public ResponseEntity<LoginURLResponseDTO> getLoginURL() {
        String loginURL = authService.createRedirectURI(AuthService.OauthType.KAKAO);

        LoginURLResponseDTO loginURLResponseDTO = new LoginURLResponseDTO();
        loginURLResponseDTO.setLoginURL(loginURL);
        return ResponseEntity.ok().body(loginURLResponseDTO);
    }

    @GetMapping("/login/oauth2/callback")
    public ResponseEntity<TokenResponseDTO> login(@RequestParam String code, @RequestParam("redirect_uri") String redirectURI) throws JsonProcessingException {

        String accessToken = authService.getAccessToken(AuthService.OauthType.KAKAO, code, redirectURI);
        OauthUserInfoResponseDTO oauthUserInfoResponseDTO = authService.getUserInfoByOauthAPI(AuthService.OauthType.KAKAO, accessToken);
        String userId = authService.login(oauthUserInfoResponseDTO.getOauthId(), oauthUserInfoResponseDTO.getUsername());
        String token = tokenManager.generateToken(userId, oauthUserInfoResponseDTO.getUsername());
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken(token);

        return ResponseEntity.ok().body(tokenResponseDTO);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponseDTO> verify(@RequestParam String token) {

        VerifyTokenResponseDTO verifyTokenResponseDTO = new VerifyTokenResponseDTO();
        try {
            tokenManager.verifyToken(token);
            verifyTokenResponseDTO.setValid(true);
            return ResponseEntity.ok().body(verifyTokenResponseDTO);
        } catch (Exception e) {
            verifyTokenResponseDTO.setValid(false);
            return ResponseEntity.ok().body(verifyTokenResponseDTO);
        }
    }



}

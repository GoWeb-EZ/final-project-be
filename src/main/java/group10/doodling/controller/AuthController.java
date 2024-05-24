package group10.doodling.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import group10.doodling.controller.response.LoginURLResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login-url")
    public ResponseEntity<LoginURLResponseDTO> getLoginURL() {  // 미구현
        LoginURLResponseDTO loginURLResponseDTO = new LoginURLResponseDTO();
        loginURLResponseDTO.setLoginURL("미구현");

        return ResponseEntity.ok().body(loginURLResponseDTO);
    }

    @GetMapping("/login/oauth2/callback")
    public ResponseEntity<String> login(@RequestParam String code) throws JsonProcessingException {
        String token = "미구현";
        return ResponseEntity.ok().body(token);
    }
}

package group10.doodling.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.doodling.component.OAuthManager;
import group10.doodling.controller.dto.request.auth.OauthUserInfoResponseDTO;
import group10.doodling.entity.User;
import group10.doodling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String login(Long oauthId, String name) {
        Optional<User> existingUser = userRepository.findByOauthId(oauthId);

        if (existingUser.isPresent()) {
            return existingUser.get().getId();
        } else {
            User user = new User();
            user.setOauthId(oauthId);
            user.setName(name);
            userRepository.save(user);

            Optional<User> newUser = userRepository.findByOauthId(oauthId);
            return newUser.map(User::getId).orElse(null);
        }
    }
}

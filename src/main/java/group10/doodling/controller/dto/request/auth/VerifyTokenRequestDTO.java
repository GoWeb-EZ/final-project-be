package group10.doodling.controller.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerifyTokenRequestDTO {
    private String token;
}

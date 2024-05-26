package group10.doodling.controller;

import group10.doodling.entity.User;
import group10.doodling.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserRepository userRepository;
    @PostMapping("/api/test-mongodb-user")
    public void testMongoDBUserInsert(@RequestParam String name) {
        User test = new User();
        test.setName(name);

        userRepository.save(test);
    }

    @GetMapping("/api/test-exception")
    public void testException() {
        throw new InternalError();
    }
}

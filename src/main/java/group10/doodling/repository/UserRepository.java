package group10.doodling.repository;

import group10.doodling.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findById(String id);
    Optional<User> findByOauthId(Long oauthId);
    Optional<User> findByName(String name);
}

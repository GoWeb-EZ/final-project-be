package group10.doodling.repository;

import group10.doodling.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
}

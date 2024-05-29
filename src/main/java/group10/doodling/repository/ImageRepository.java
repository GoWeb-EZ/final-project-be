package group10.doodling.repository;

import group10.doodling.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, UUID> {

}

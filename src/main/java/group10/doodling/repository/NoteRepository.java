package group10.doodling.repository;

import group10.doodling.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends MongoRepository<Note, UUID> {

    List<Note> findByUserId(String userId);
    Optional<Note> findById(String id);
}

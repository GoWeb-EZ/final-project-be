package group10.doodling.service;

import group10.doodling.entity.Image;
import group10.doodling.entity.Note;
import group10.doodling.entity.User;
import group10.doodling.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String saveUserNote(String userId, Note note) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Note> notes = user.getNotes();
            notes.add(note);
            user.setNotes(notes);
            return userRepository.save(user).getId();

        } else {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    public void updateUserNoteList(String userId, Note updatedNote) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getNotes().removeIf(note -> note.getId().equals(updatedNote.getId()));
        user.getNotes().add(updatedNote);
        userRepository.save(user);
    }

    public void deleteUserNote(String userId, Note note) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getNotes().remove(note);
        userRepository.save(user);
    }
}

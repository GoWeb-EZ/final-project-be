package group10.doodling.service;

import group10.doodling.entity.Image;
import group10.doodling.entity.Note;
import group10.doodling.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ImageService imageService;

    public String createNote(String userId, String title, String content, String createdAt, List<MultipartFile> images, List<String> imageTexts) throws IOException {

        Note note = createBaseNoteEntity(userId, title, content, createdAt);
        Note savedNote = noteRepository.save(note);

        List<Image> savedImages = imageService.createImage(savedNote.getId(), images, imageTexts);
        List<Image> result = concatImageList(savedNote.getImages(), savedImages);

        savedNote.setImages(result);

        return savedNote.getId();
    }

    private Note createBaseNoteEntity(String userId, String title, String content, String createdAt) {
        return Note.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .createdAt(createdAt).build();
    }

    private List<Image> concatImageList(List<Image> list1, List<Image> list2) {
        return Stream.of(list1, list2).flatMap(Collection::stream).toList();
    }
}

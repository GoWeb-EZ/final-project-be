package group10.doodling.service;

import group10.doodling.controller.dto.common.ImageMetaDataDTO;
import group10.doodling.controller.dto.request.note.updateNote.UpdateNoteRequestDTO;
import group10.doodling.controller.dto.response.note.readNote.detail.NoteDetailDataDTO;
import group10.doodling.controller.dto.response.note.readNote.detail.ReadDetailNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.preview.NotePreviewDataDTO;
import group10.doodling.controller.dto.response.note.readNote.preview.ReadPreviewNoteResponseDTO;
import group10.doodling.entity.Image;
import group10.doodling.entity.Note;
import group10.doodling.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ImageService imageService;

    public Note createNote(String userId, String title, String content, List<String> tags, String createdAt, List<MultipartFile> images, List<String> imageTexts) throws IOException {

        Note baseNoteEntity = createBaseNoteEntity(userId, title, content, tags, createdAt);
        Note note = noteRepository.save(baseNoteEntity);

        List<Image> savedImages = imageService.createImage(note.getId(), images, imageTexts);
        List<Image> result = concatList(note.getImages(), savedImages);

        note.setImages(result);

        return noteRepository.save(note);
    }

    public ReadPreviewNoteResponseDTO getPreviewNote(String userId) {
        List<Note> notes = noteRepository.findByUserId(userId);

        List<NotePreviewDataDTO> notePreviews = notes.stream().map(note -> {
            NotePreviewDataDTO previewData = new NotePreviewDataDTO();
            previewData.setNoteId(note.getId());
            previewData.setDate(note.getCreatedAt());
            previewData.setTags(note.getTags());
            previewData.setPreview(note.getContent().substring(0, Math.min(note.getContent().length(), 40)));
            return previewData;
        }).collect(Collectors.toList());

        ReadPreviewNoteResponseDTO responseDTO = new ReadPreviewNoteResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("내 노트 리스트 조회에 성공했습니다.");
        responseDTO.setResult(notePreviews);

        return responseDTO;
    }

    public ReadDetailNoteResponseDTO getDetailNote(String noteId, String userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        NoteDetailDataDTO detailData = new NoteDetailDataDTO();
        detailData.setContent(note.getContent());
        detailData.setDate(note.getCreatedAt());
        detailData.setTags(note.getTags());
        detailData.setUserId(note.getUserId());

        List<ImageMetaDataDTO> imageMetaDataList = note.getImages().stream().map(image -> {
            ImageMetaDataDTO metaDataDTO = new ImageMetaDataDTO();
            metaDataDTO.setText(image.getText());
            metaDataDTO.setFileName(image.getStoreFileName());
            return metaDataDTO;
        }).collect(Collectors.toList());

        detailData.setImageMetaDataList(imageMetaDataList);

        ReadDetailNoteResponseDTO responseDTO = new ReadDetailNoteResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("노트 상세 조회에 성공했습니다.");
        responseDTO.setNoteId(note.getId());
        responseDTO.setResult(detailData);

        return responseDTO;
    }

    public Note updateNote(String noteId, List<MultipartFile> images, UpdateNoteRequestDTO updateNoteRequestDTO) throws IOException {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setContent(updateNoteRequestDTO.getContent());
        note.setTags(updateNoteRequestDTO.getTags());
        note.setCreatedAt(updateNoteRequestDTO.getDate());

        if (images == null) {
            images = Collections.emptyList();
        }

        List<Image> updatedImages = imageService.createImage(note.getId(), images, updateNoteRequestDTO.getImageTexts());
        note.setImages(updatedImages);

        return noteRepository.save(note);
    }

    public void deleteNote(String noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }

    private Note createBaseNoteEntity(String userId, String title, String content, List<String> tags, String createdAt) {
        return Note.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .tags(tags)
                .createdAt(createdAt).build();
    }

    private List<Image> concatList(List<Image> list1, List<Image> list2) {
        if (list1 == null)
            list1 = Collections.emptyList();
        if (list2 == null)
            list2 = Collections.emptyList();

        return Stream.of(list1, list2).flatMap(Collection::stream).toList();
    }
}

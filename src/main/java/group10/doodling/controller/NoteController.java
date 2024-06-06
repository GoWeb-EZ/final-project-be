package group10.doodling.controller;

import group10.doodling.controller.dto.request.note.createNote.CreateNoteRequestDTO;
import group10.doodling.controller.dto.request.note.updateNote.UpdateNoteRequestDTO;
import group10.doodling.controller.dto.response.note.createNote.CreateNoteResponseDTO;
import group10.doodling.controller.dto.response.note.deleteNote.DeleteNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.detail.ReadDetailNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.preview.ReadPreviewNoteResponseDTO;
import group10.doodling.controller.dto.response.note.updateNote.UpdateNoteResponseDTO;
import group10.doodling.entity.Note;
import group10.doodling.service.NoteService;
import group10.doodling.service.UserService;
import group10.doodling.util.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<CreateNoteResponseDTO> createNote(@UserId String userId,
                                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                            @RequestPart(value = "CreateNoteRequestDTO", required = false) CreateNoteRequestDTO createNoteRequestDTO) throws IOException {

        String title = createNoteRequestDTO.getTitle();
        String content = createNoteRequestDTO.getContent();
        List<String> tags = createNoteRequestDTO.getTags();
        List<String> imageTexts = createNoteRequestDTO.getImageTexts();
        String createdAt = createNoteRequestDTO.getCreatedAt();

        Note note = noteService.createNote(userId, title, content, tags, createdAt, images, imageTexts);

        userService.saveUserNote(userId, note);

        CreateNoteResponseDTO createNoteResponseDTO = new CreateNoteResponseDTO();
        createNoteResponseDTO.setSuccess(true);
        createNoteResponseDTO.setMessage("노트 저장에 성공했습니다.");

        return ResponseEntity.ok().body(createNoteResponseDTO);
    }

    @GetMapping("/preview")
    public ResponseEntity<ReadPreviewNoteResponseDTO> getNotePreview(@UserId String userId) {
        ReadPreviewNoteResponseDTO responseDTO = noteService.getPreviewNote(userId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/detail")
    public ResponseEntity<ReadDetailNoteResponseDTO> getNoteDetail(@UserId String userId, @RequestParam String noteId) {
        ReadDetailNoteResponseDTO responseDTO = noteService.getDetailNote(noteId, userId);
        return ResponseEntity.ok(responseDTO);
    }


    @PatchMapping
    public ResponseEntity<UpdateNoteResponseDTO> updateNote(@UserId String userId,
                                                            @RequestParam String noteId,
                                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                            @RequestPart(value = "UpdateNoteRequestDTO", required = false) UpdateNoteRequestDTO updateNoteRequestDTO) throws IOException {

        Note note = noteService.updateNote(noteId, images, updateNoteRequestDTO);
        userService.updateUserNoteList(userId, note);

        UpdateNoteResponseDTO response = new UpdateNoteResponseDTO();
        response.setSuccess(true);
        response.setMessage("노트 수정에 성공했습니다.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteNoteResponseDTO> deleteNote(@UserId String userId, @RequestParam String noteId) {
        noteService.deleteNote(noteId);
        userService.deleteUserNote(userId, noteId);

        DeleteNoteResponseDTO response = new DeleteNoteResponseDTO();
        response.setSuccess(true);
        response.setMessage("노트 삭제 성공");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ReadPreviewNoteResponseDTO> getNotePreviewBySearchTag(@UserId String userId, @RequestParam String tag) {

    }
}


package group10.doodling.controller;

import group10.doodling.controller.dto.request.note.createNote.CreateNoteRequestDTO;
import group10.doodling.controller.dto.request.note.updateNote.UpdateNoteRequestDTO;
import group10.doodling.controller.dto.response.note.createNote.CreateNoteResponseDTO;
import group10.doodling.controller.dto.response.note.deleteNote.DeleteNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.detail.ReadDetailNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.preview.ReadPreviewNoteResponseDTO;
import group10.doodling.controller.dto.response.note.updateNote.UpdateNoteResponseDTO;
import group10.doodling.entity.Note;
import group10.doodling.entity.User;
import group10.doodling.repository.UserRepository;
import group10.doodling.service.NoteService;
import group10.doodling.service.UserService;
import group10.doodling.util.annotation.UserId;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;


    @PostMapping
    public ResponseEntity<CreateNoteResponseDTO> createNote(@UserId String userId,
                                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                            @RequestPart (value = "CreateNoteRequestDTO", required = false) CreateNoteRequestDTO createNoteRequestDTO) throws IOException {

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
    public ResponseEntity<ReadPreviewNoteResponseDTO> readPreviewNote(@UserId String userId) {
        ReadPreviewNoteResponseDTO readPreviewNoteResponseDTO = new ReadPreviewNoteResponseDTO();

        /*
        * 노트 읽기(Preview) 로직 추가 후 DTO 에 저장 후 return
        * */

        readPreviewNoteResponseDTO.setMessage("미구현");
        readPreviewNoteResponseDTO.setSuccess(false);

        return ResponseEntity.ok().body(readPreviewNoteResponseDTO);
    }

    @GetMapping("/detail")
    public ResponseEntity<ReadDetailNoteResponseDTO> readDetailNote(@UserId String userId, @RequestParam String noteId) {
        ReadDetailNoteResponseDTO readDetailNoteResponseDTO = new ReadDetailNoteResponseDTO();

        /*
         * 노트 읽기(Detail) 로직 추가 후 DTO 에 저장 후 return
         * */

        readDetailNoteResponseDTO.setNoteId("미구현");
        readDetailNoteResponseDTO.setSuccess(false);
        readDetailNoteResponseDTO.setMessage("미구현");

        return ResponseEntity.ok().body(readDetailNoteResponseDTO);
    }

    @PatchMapping
    public ResponseEntity<UpdateNoteResponseDTO> updateNote(@UserId String userId, @RequestBody UpdateNoteRequestDTO updateNoteRequestDTO) {
        UpdateNoteResponseDTO updateNoteResponseDTO = new UpdateNoteResponseDTO();

        /*
         * 노트 수정 로직 추가 후 DTO 에 저장 후 return
         * */

        updateNoteResponseDTO.setMessage("미구현");
        updateNoteResponseDTO.setSuccess(false);

        return ResponseEntity.ok().body(updateNoteResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<DeleteNoteResponseDTO> deleteNote(@UserId String userId, @RequestParam String noteId) {
        DeleteNoteResponseDTO deleteNoteResponseDTO = new DeleteNoteResponseDTO();

        /*
         * 노트 삭제 로직 추가 후 DTO 에 저장 후 return
         * */

        deleteNoteResponseDTO.setMessage("미구현");
        deleteNoteResponseDTO.setSuccess(false);

        return ResponseEntity.ok().body(deleteNoteResponseDTO);
    }


}

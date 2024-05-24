package group10.doodling.controller;

import group10.doodling.controller.dto.request.note.createNote.CreateNoteRequestDTO;
import group10.doodling.controller.dto.response.note.createNote.CreateNoteResponseDTO;
import group10.doodling.controller.dto.response.note.deleteNote.DeleteNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.detail.ReadDetailNoteResponseDTO;
import group10.doodling.controller.dto.response.note.readNote.preview.ReadPreviewNoteResponseDTO;
import group10.doodling.controller.dto.response.note.updateNote.UpdateNoteResponseDTO;
import group10.doodling.util.annotation.UserId;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/note")
public class NoteController {


    @PostMapping
    public ResponseEntity<CreateNoteResponseDTO> createNote(@UserId String userId, @RequestBody CreateNoteRequestDTO createNoteRequestDTO) {
        CreateNoteResponseDTO createNoteResponseDTO = new CreateNoteResponseDTO();

        /*
         * 노트 저장 로직 추가 후 DTO 에 저장 후 return
         * */

        createNoteResponseDTO.setSuccess(false);
        createNoteResponseDTO.setMessage("미구현");

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
    public ResponseEntity<UpdateNoteResponseDTO> updateNote(@UserId String userId, @RequestParam String noteId) {
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

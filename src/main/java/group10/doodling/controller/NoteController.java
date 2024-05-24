package group10.doodling.controller;

import group10.doodling.controller.response.CreateNoteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @PostMapping
    public ResponseEntity<CreateNoteResponseDTO> createNote() { // 미구현
        CreateNoteResponseDTO createNoteResponseDTO = new CreateNoteResponseDTO();
        createNoteResponseDTO.setSuccess(false);
        createNoteResponseDTO.setMessage("미구현");

        return ResponseEntity.ok().body(createNoteResponseDTO);
    }

}

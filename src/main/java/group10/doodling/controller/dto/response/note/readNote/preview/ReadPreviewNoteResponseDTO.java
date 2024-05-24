package group10.doodling.controller.dto.response.note.readNote.preview;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReadPreviewNoteResponseDTO {
    private boolean isSuccess;
    private String message;
    private List<NotePreviewDataDTO> result = new ArrayList<>();
}


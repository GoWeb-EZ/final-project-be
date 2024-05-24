package group10.doodling.controller.response.note.readNote.detail;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReadDetailNoteResponseDTO {

    private boolean isSuccess;
    private String message;
    private String noteId;
    private NoteDetailDataDTO result;
}

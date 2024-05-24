package group10.doodling.controller.response.note.readNote.preview;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NotePreviewDataDTO {
    private String noteId;
    private String date;
    private List<String> tags = new ArrayList<>();
    private String preview;
}

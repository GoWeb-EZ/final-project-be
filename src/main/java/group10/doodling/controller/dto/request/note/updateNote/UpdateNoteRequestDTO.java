package group10.doodling.controller.dto.request.note.updateNote;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UpdateNoteRequestDTO {
    private String date;
    private List<String> tags;
    private String content;
    private List<String> imageTexts = new ArrayList<>();
}

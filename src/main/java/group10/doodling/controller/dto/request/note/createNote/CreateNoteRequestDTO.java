package group10.doodling.controller.dto.request.note.createNote;

import group10.doodling.controller.dto.common.TransformedImageDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CreateNoteRequestDTO {
    private String userId;
    private String date;
    private List<String> tags;
    private String content;
    private List<TransformedImageDTO> images = new ArrayList<>();
}

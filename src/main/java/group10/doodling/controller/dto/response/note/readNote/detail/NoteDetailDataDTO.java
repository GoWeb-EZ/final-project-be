package group10.doodling.controller.dto.response.note.readNote.detail;

import group10.doodling.controller.dto.common.TransformedImageDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoteDetailDataDTO {

    private String content;
    private String date;
    private List<TransformedImageDTO> images = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private String userId;
}

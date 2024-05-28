package group10.doodling.controller.dto.request.note.createNote;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CreateNoteRequestDTO {
    private String userId;
    private String date;
    private List<String> tags;
    private String content;
    private List<String> imageTexts = new ArrayList<>();
}

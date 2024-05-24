package group10.doodling.controller.response.note.readNote.detail;

import lombok.Getter;
import lombok.Setter;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoteDetailDataDTO {

    private String content;
    private String date;
    private List<Buffer> images = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private String userId;
}

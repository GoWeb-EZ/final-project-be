package group10.doodling.controller.dto.response.note.readNote.detail;

import group10.doodling.controller.dto.common.ImageMetaDataDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoteDetailDataDTO {

    private String content;
    private String date;
    private List<ImageMetaDataDTO> imageMetaDataList = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private String userId;
}

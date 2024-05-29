package group10.doodling.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("note")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Note extends UuidIdentifiedEntity {

    private String userId;
    private String title;
    private String content;
    private String createdAt;

    @DBRef
    private List<Image> images = new ArrayList<>();

}

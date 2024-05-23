package group10.doodling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
@Document("note")
@AllArgsConstructor
@Getter
@Setter
public class Note extends UuidIdentifiedEntity {
    private String userId;
    private String title;
    private String content;

}

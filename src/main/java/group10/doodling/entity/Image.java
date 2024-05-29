package group10.doodling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("note")
@AllArgsConstructor
@Getter
@Setter
public class Image extends UuidIdentifiedEntity {

    private String noteId;
    private String storeFileName;
    private String text;
}

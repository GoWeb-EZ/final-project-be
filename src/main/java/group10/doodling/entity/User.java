package group10.doodling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class User extends UuidIdentifiedEntity {
    private String oauthId;
    private String name;
    @DBRef
    private List<Note> notes = new ArrayList<>();
}

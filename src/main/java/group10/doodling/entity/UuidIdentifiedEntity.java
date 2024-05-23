package group10.doodling.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public abstract class UuidIdentifiedEntity {
    @Id
    protected String id;
    public void setId(String id) {
        if(this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }
        this.id = id;
    }
}

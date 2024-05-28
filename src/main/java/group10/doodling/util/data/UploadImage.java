package group10.doodling.util.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImage {

    private String uploadFileName;
    private String storeFileName;

}

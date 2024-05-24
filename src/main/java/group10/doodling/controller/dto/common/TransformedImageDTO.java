package group10.doodling.controller.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.nio.Buffer;

@Getter @Setter
public class TransformedImageDTO {

    private Buffer image;
    private String text;
}

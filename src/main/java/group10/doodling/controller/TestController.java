package group10.doodling.controller;

import group10.doodling.component.ImageManager;
import group10.doodling.controller.dto.common.ImageMetaDataDTO;
import group10.doodling.controller.dto.request.note.createNote.CreateNoteRequestDTO;
import group10.doodling.entity.User;
import group10.doodling.repository.UserRepository;
import group10.doodling.util.data.UploadImage;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final ImageManager imageManager;

    @PostMapping("/api/test-mongodb-user")
    public void testMongoDBUserInsert(@RequestParam String name) {
        User test = new User();
        test.setName(name);

        userRepository.save(test);
    }

    @GetMapping("/api/test-exception")
    public void testException() {
        throw new InternalError();
    }

    @PostMapping("/api/test-upload")
    public ResponseEntity<List<ImageMetaDataDTO>> testUpload(@RequestParam("images") List<MultipartFile> images,
                                                       @RequestPart(required = false) CreateNoteRequestDTO createNoteRequestDTO) throws IOException {
        List<UploadImage> uploadImages = imageManager.saveImages(images);
        List<ImageMetaDataDTO> imageMetaDataList = new ArrayList<>();
        for (UploadImage uploadImage: uploadImages) {
            ImageMetaDataDTO imageMetaDataDTO = new ImageMetaDataDTO();
            imageMetaDataDTO.setText("test text");
            imageMetaDataDTO.setFileName(uploadImage.getStoreFileName());
            imageMetaDataList.add(imageMetaDataDTO);
        }
        return ResponseEntity.ok().body(imageMetaDataList);
    }
}

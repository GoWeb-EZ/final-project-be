package group10.doodling.service;

import group10.doodling.component.ImageManager;
import group10.doodling.entity.Image;
import group10.doodling.repository.ImageRepository;
import group10.doodling.util.data.UploadImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageManager imageManager;
    private final ImageRepository imageRepository;

    public List<Image> createImage(String noteId, List<MultipartFile> images, List<String> imageTexts) throws IOException {
        List<UploadImage> uploadImages = imageManager.saveImages(images);
        List<Image> noteImages = IntStream.range(0, uploadImages.size())
                .mapToObj(i -> new Image(noteId, uploadImages.get(i).getStoreFileName(), imageTexts.get(i)))
                .toList();
        System.out.println("noteImages = " + noteImages);
        return imageRepository.saveAll(noteImages);
    }

}

package group10.doodling.component;

import group10.doodling.util.data.UploadImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageManager {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadImage> saveImages(List<MultipartFile> images)
            throws IOException {
        List<UploadImage> storeFileResult = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                storeFileResult.add(saveImage(image));
            }
        }
        return storeFileResult;
    }

    public UploadImage saveImage(MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            return null;
        }

        String originalFilename = image.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        image.transferTo(new File(getFullPath(storeFileName)));
        return new UploadImage(originalFilename, storeFileName);

    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + "png";
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

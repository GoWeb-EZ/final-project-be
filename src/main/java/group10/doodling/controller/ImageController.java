package group10.doodling.controller;

import group10.doodling.component.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageManager imageManager;

    @ResponseBody
    @GetMapping("api/image")
    public ResponseEntity<Resource> downloadImage(@RequestParam("file_name") String fileName) throws
            IOException {
        String fullPath = imageManager.getFullPath(fileName);
        //MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(Paths.get(fullPath)));
        UrlResource resource = new UrlResource("file:" + fullPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(resource);
    }

}

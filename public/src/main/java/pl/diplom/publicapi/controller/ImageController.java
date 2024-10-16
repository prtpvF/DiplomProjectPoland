package pl.diplom.publicapi.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/public/image")
public class ImageController {

        private final String uploadDir = "uploads/";

        @GetMapping("/")
        public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String filename) {
            File file = new File(uploadDir + filename); // Укажите путь к файлу

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(file.length())
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

}

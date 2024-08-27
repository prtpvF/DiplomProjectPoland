package pl.diplom.admin.service;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

        private final String uploadDir = "uploads/";

        public String savePhotoLocal(MultipartFile file) {
            try {
                File directory = new File(uploadDir);
                if(!directory.exists()) {
                    directory.mkdir();
                }

                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                Files.copy(file.getInputStream(),
                        filePath,
                        StandardCopyOption.REPLACE_EXISTING);
                return filePath.toAbsolutePath().toString();
            }catch (IOException e){
             e.printStackTrace();
           return "";
            }
        }

        public ResponseEntity<Resource> getPhoto(String fileName) {
            try {
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                if (resource.exists()) {
                    // Возвращаем файл как ресурс с типом контента
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG) // Можно определить тип в зависимости от файла
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
}

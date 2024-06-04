package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/users")
public class UserAvatarController {


    private static final String UPLOAD_DIR = "uploads/avatars/";

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/{userId}/avatars")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable("userId") int userId) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Not found file!", HttpStatus.NOT_FOUND);
        }
        if(!userRepository.existsById(userId)){
            return new ResponseEntity<>("Not found Id",HttpStatus.OK);
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file vào thư mục avatars
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + fileExtension;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            // Lưu đường dẫn avatar vào cơ sở dữ liệu
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setAvatar(newFileName);
            userRepository.save(user);

            return ResponseEntity.ok("You successfully uploaded " + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fail to upload",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{userId}/avatars")
    public ResponseEntity<?> getAvatar(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        String avatarFileName = user.getAvatar();
        if (avatarFileName == null || avatarFileName.isEmpty()) {
            return new ResponseEntity<>("User does not have an avatar", HttpStatus.OK);
        }


        File file = new File(UPLOAD_DIR+user.getAvatar());

        System.out.println();

        if (file.exists()) {
            Resource resource = new FileSystemResource(file);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{userId}/avatars")
    public ResponseEntity<?> deleteAvatar(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }

        String avatarFileName = user.getAvatar();
        if (avatarFileName != null && !avatarFileName.isEmpty()) {
            File file = new File(UPLOAD_DIR + avatarFileName);
            if (file.exists()) {
                if (file.delete()) {
                    user.setAvatar(null);
                    userRepository.save(user);
                    return new ResponseEntity<>("Avatar deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Failed to delete avatar", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("Avatar file not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User has no avatar", HttpStatus.BAD_REQUEST);
        }
    }
}




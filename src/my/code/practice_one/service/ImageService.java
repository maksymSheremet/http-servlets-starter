package my.code.practice_one.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.code.practice_one.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {
    private final String basePath = PropertiesUtil.get("image.base.url");
    private static final ImageService INSTANCE = new ImageService();

    public static ImageService getInstance() {
        return INSTANCE;
    }

    public void upload(String imagePath, InputStream imageContent) {
        var imageFullPath = Path.of(basePath, imagePath);
        try (imageContent){
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

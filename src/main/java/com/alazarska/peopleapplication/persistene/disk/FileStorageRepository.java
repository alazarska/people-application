package com.alazarska.peopleapplication.persistene.disk;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileStorageRepository {

    @Value("${storage.folder}")
    private String storageFolder;

    @SneakyThrows
    public void save(String filename, InputStream inputStream) {
        Path filePath = Path.of(storageFolder).resolve(filename).normalize();
        Files.copy(inputStream, filePath);
    }

    @SneakyThrows
    public Resource findByName(String fileName) {
        Path filePath = Path.of(storageFolder).resolve(fileName).normalize();
        return new UrlResource(filePath.toUri());
    }

    @SneakyThrows
    public void deleteFileIfExist(String fileName) {
        if (!fileName.equals("default-avatar.jpg")) {
            Path filePath = Path.of(storageFolder).resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        }
    }
}

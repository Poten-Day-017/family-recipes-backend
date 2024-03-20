package com.bside.familyrecipes.recipes.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.error.exception.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {

    private final String fileUrl;
    private final Path fileLocation;

    public StorageService(@Value("${file.store.url:localhost:8080/static}") String fileUrl,
        @Value("${file.store.path:./static}") String filePath) {
        this.fileUrl = fileUrl;
        this.fileLocation = Paths.get(filePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String storeFile(MultipartFile file) {
        var fileName = StringUtils.cleanPath(String.valueOf(file.getOriginalFilename()));
        var storedName = replaceRandomFileName(fileName);
        var resolve = fileLocation.resolve(storedName);
        try {
            Files.copy(file.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("", e);
            throw new FileUploadException();
        }
        return fileUrl + "/" + storedName;
    }

    public Map<String, String> storeFiles(Map<String, MultipartFile> multipartFileMap) {
        return multipartFileMap.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> storeFile(entry.getValue())));
    }

    private String replaceRandomFileName(String originalFileName) {
        StringBuilder sb = new StringBuilder(RandomStringUtils.randomAlphabetic(40));
        if (originalFileName.lastIndexOf(".") > 0) {
            var fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            sb.append(fileExtension);
        }
        return sb.toString();
    }
}

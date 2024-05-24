package com.deadpool.blog.blogbackend.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Generate a random file name with the same extension as the original file
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();

        assert originalFileName != null; // Ensure the file has a name
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = randomId + extension;

        // Construct the file path
        Path filePath = Paths.get(path, newFileName);

        // Ensure the directory exists
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create the new file and copy the input stream to it
        Files.copy(file.getInputStream(), filePath);

        return newFileName;
    }

    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        }

        return inputStream;
    }

}

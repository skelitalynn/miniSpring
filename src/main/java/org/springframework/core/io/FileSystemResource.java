package org.springframework.core.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
public class FileSystemResource implements Resource{
    private final String path;

    public FileSystemResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try{
            Path filePath = Path.of(this.path);
            return Files.newInputStream(filePath);
        }catch (NoSuchFileException ex){
            throw new FileNotFoundException(ex.getMessage());
        }
    }
}

package com.example.demo;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class UploadFilesService {

    // Ścieżka do folderu głównego
    private final Path rootLocation = Paths.get("upload-files");

    // Metoda pobiera wszystkie pliki z zadanej ścieżki na serwerze
    // oraz odrzuca pliki, które są folderami
    public Stream<Path> getAllFiles() throws IOException {
        return Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(rootLocation::relativize);
    }

    // Metoda służy do stworzenia z pliku takiego zasobu, który będzie
    // mógł być wykorzystany na froncie
    public Resource getResource(String filename) throws MalformedURLException {
        Path file = getLocation(filename);
        return new UrlResource(file.toUri());
    }

    // Metoda pobiera "wykonyuwalną" ścieżkę do pliku
    private Path getLocation(String filename) {
        return rootLocation.resolve(filename);
    }

    // Metoda pobiera nazwę wrzuconego przez użytkownika pliku, generuje dla niego ścieżkę
    // oraz umieszcza ten plik na naszym serwerze
    public void uploadFile(MultipartFile file) {
        Path destinationPath = rootLocation.resolve(Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

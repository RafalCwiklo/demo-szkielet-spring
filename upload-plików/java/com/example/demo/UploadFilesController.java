package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UploadFilesController {

    private final UploadFilesService uploadFilesService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", uploadFilesService.getAllFiles().map(
                path -> MvcUriComponentsBuilder.fromMethodName(UploadFilesController.class,
                        "serveFile", path.getFileName().toString())
                        .build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadFileForm";
    }

    // Metoda odpowiada za wczytanie realnego pliku z serwera oraz zwrócenie
    // odpowiedzi, która pozwoli na froncie wykorzystać ten plik jako załącznik
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {
        Resource resource = uploadFilesService.getResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    // Metoda pozwala na wrzucenie pliku z frontu (po stronie backendu korzystamy
    // z klasy MultipartFile do odebrania pliku z frontu). I przekierowuje do
    // strony główenj, gdzie wyświetlone są wszystkie pliki
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        uploadFilesService.uploadFile(file);

        return "redirect:/";
    }

}

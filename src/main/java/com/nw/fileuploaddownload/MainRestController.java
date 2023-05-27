package com.nw.fileuploaddownload;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/")
public class MainRestController {
    private final DocumentRepository documentRepository;

    public MainRestController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/files")
    public ResponseEntity<List<Document>> getAllFiles() {
        List<Document> files = documentRepository.findAll();
        return files != null
                ? new ResponseEntity<>(files, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Document> downloadFileById(@PathVariable(value = "id") long id, HttpServletResponse response) throws Exception {
        Optional<Document> result = documentRepository.findById(id);

        if(!result.isPresent()) {
            throw new Exception("Could not find document with ID: " + id);
        }

        Document document = result.get();
        response.setContentType("application/octed-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();

        return document != null
                ? new ResponseEntity<>(document, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadFile(@RequestParam("document") MultipartFile multipartFile,
                                               RedirectAttributes ra) throws IOException {


        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long fileSize = multipartFile.getSize();

        if (fileName.endsWith(".txt") || fileName.endsWith(".csv") || fileSize > 100000) {
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } else {
            Document document = new Document();
            document.setName(fileName);
            document.setContent(multipartFile.getBytes());
            document.setSize(multipartFile.getSize());
            document.setUploadTime(new Date());

            documentRepository.save(document);

            ra.addFlashAttribute("message", "Файл загружен успешно");

        }
         return new ResponseEntity<>(HttpStatus.OK);

    }
}

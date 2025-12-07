package com.fileshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileSharingController {

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger log = Logger.getLogger(FileSharingController.class.getName());

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String filename) {
        try {
            var filetodownload = fileStorageService.getDownloadFile(filename);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(filetodownload.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(filetodownload.toPath())));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/viewall")
    public String viewAllFiles() {
        StringBuilder filemetadata = new StringBuilder();
        try {
            File[] temp = fileStorageService.getAllFiles();
            filemetadata = new StringBuilder("File Name" + "\t" +
                    "File size" + "\t" +
                    "Upload Timestamp" + "\t" +
                    "Unique ID: " + "\n");
            for (File file : temp) {
                String uniqueid = file.getName() + file.lastModified();
                Date lastmodified = new Date(file.lastModified());
                filemetadata.append(file.getName()).append("\t").append(file.length()).append("\t").append("Bytes").append("\t").append(lastmodified).append("\t").append(uniqueid).append("\n");
            }
            return String.valueOf(filemetadata);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Files could not be viewed", e);
        }
        return String.valueOf(filemetadata = new StringBuilder("no metadata found"));
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        try{
            fileStorageService.saveFile(file);
            return file.getOriginalFilename();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during upload", e);
        }
        return file.getOriginalFilename();
    }
}

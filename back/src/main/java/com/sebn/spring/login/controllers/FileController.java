package com.sebn.spring.login.controllers;



import com.sebn.spring.login.models.*;
import com.sebn.spring.login.payload.message.*;
import com.sebn.spring.login.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.support.*;

import java.util.*;
import java.util.stream.*;

@Controller
@CrossOrigin("http://localhost:4200")
public class FileController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping("/api/upload")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            FileDB fileDb = storageService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new UploadFileResponse(fileDb.getId(), message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UploadFileResponse(null, message));
        }
    }

    @GetMapping("/api/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getId(),
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/api/files/{id}")
    public ResponseEntity<ResponseFile> getFile(@PathVariable long id) {
        FileDB fileDB = storageService.getFile(id);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileDB.getId().toString())
                .toUriString();
        ResponseFile file = new ResponseFile(
                fileDB.getId(),
                fileDB.getName(),
                fileDownloadUri,
                fileDB.getType(),
                fileDB.getData().length);
        return ResponseEntity.status(HttpStatus.OK).body(file);
    }
    @GetMapping("/api/files/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable long id) {
        FileDB fileDB = storageService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}
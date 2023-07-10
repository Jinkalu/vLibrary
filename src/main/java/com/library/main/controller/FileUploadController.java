package com.library.main.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.main.upload.FileUploadService;
import com.library.main.vo.FileResponse;
import com.library.main.vo.FileResponseMessage;
import com.library.main.vo.ItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/upload-files",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileResponseMessage> uploadFiles(@RequestParam("info") String info,
                                                           @RequestParam("file") MultipartFile[] files) {


        String message = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ItemInfo itemInfo = objectMapper.readValue(info, ItemInfo.class);
            List<String> fileName = new ArrayList<>();
            Arrays.stream(files).forEach(file -> {
                fileUploadService.save(file);
                fileName.add(file.getOriginalFilename());

            });
            message = "File(s) uploaded successfully " + fileName;
            return ResponseEntity.status(OK).body(FileResponseMessage.builder().message(message).build());
        } catch (Exception ex) {
            return ResponseEntity.status(EXPECTATION_FAILED).body(new FileResponseMessage(ex.getMessage()));
        }
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getFileByName(@PathVariable String fileName) {

        Resource resource = fileUploadService.getFileByName(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; fileName=\"" + resource.getFilename() + "\"").body(resource);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        List<FileResponse> files = fileUploadService.loadAllFiles()
                .map(file -> FileResponse.builder()
                        .fileName(file.getFileName().toString())
                        .url(MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class,
                                        "getFileByName",
                                        file.getFileName().toString()).build().toString())
                        .build()).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

}

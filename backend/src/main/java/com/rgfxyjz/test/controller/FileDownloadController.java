///**
// * @FileName FileDownloadController
// * @Description TODO
// * @Author 57004
// * @date 2024-08-25
// **/
//package com.rgfxyjz.test.controller;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/download")
//public class FileDownloadController {
//
//    @GetMapping
//    public ResponseEntity<Resource> downloadFile(@RequestParam String fileUrl) {
//        try {
//            Path filePath = Paths.get(fileUrl);
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(null);
//            }
//        } catch (MalformedURLException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(null);
//        }
//    }
//}

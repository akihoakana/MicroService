package com.example.UploadFile8082.controller;

import com.example.UploadFile8082.entity.FileDataEntity;
import com.example.UploadFile8082.facade.ImageReaderFacade;
import com.example.UploadFile8082.facade.ImageWriterFacade;
import com.example.UploadFile8082.repository.StorageRepository;
import com.example.UploadFile8082.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/image")
public class ImageController {
    @Autowired
    private ImageReaderFacade imageReaderFacade;
    @Autowired
    private ImageWriterFacade imageWriterFacade;
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageRepository storageRepository;

    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;

    @Value("${myDetailService.springJPA8081.findByEmailId}")
    private String urlDetailFindByEmailId;

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/hello")
    public ResponseEntity<?> hello() throws IOException {
        System.out.println("hello image");
        return ResponseEntity.ok("hello");
    }

    @PostMapping(value = "/fileSystem", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam(value = "image") List<MultipartFile> file) throws IOException {
        return ResponseEntity.ok(imageReaderFacade.uploadImageToFIleSystem(file));
    }

    @PostMapping("/status/uploadImageTest")
    public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> multipartFile
            , @RequestParam("idUser") int statusId, @RequestHeader HttpHeaders httpHeaders) {
        System.out.println("multipartFile = " + multipartFile);
        multipartFile.forEach(s-> System.out.println("s.getOriginalFilename() = " + s.getOriginalFilename()));
        System.out.println("httpHeaders = " + httpHeaders);
        imageWriterFacade.uploadImageTest(multipartFile, statusId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/fileSystem/download/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
        if (imageData == null) {
            System.out.println("null");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(storageService.downloadFileFromFileSystem(fileName)));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }
    }

    @PostMapping("/mailAttachs/email/{email}")
    public ResponseEntity<?> sendAttachEmailFromFilesDB(@PathVariable String email,
                                                        @RequestBody List<FileDataEntity> fileDataEntities) throws IOException, MessagingException {
        imageWriterFacade.sendAttachEmailFromFilesDB(email, fileDataEntities);
        return ResponseEntity.ok("Check mail" + email);
    }

    @RequestMapping("/mail/email/{email}")
    public ResponseEntity<?> sendEmailWithConfigPathFileByEmail(@PathVariable String email) throws IOException, MessagingException {
        imageWriterFacade.sendEmailWithConfigPathFileByEmail(email);
        return ResponseEntity.ok("Check mail");
    }

    @RequestMapping("/mailAttach/email/{email}")
    public ResponseEntity<?> sendAttachEmailByEmail(@PathVariable String email, @RequestParam("image") List<MultipartFile> multipartFile) throws IOException, MessagingException {
        imageWriterFacade.sendAttachEmailByEmail(email, multipartFile);
        return ResponseEntity.ok("Check mail");
    }

    @PostMapping("/mail/id/{id}")
    public ResponseEntity<?> sendEmailWithConfigPathFileById(@PathVariable int id
            , @RequestHeader HttpHeaders httpHeaders) throws IOException, MessagingException {
        imageWriterFacade.sendEmailWithConfigPathFileById(id, httpHeaders);
        return ResponseEntity.ok("Check mail");
    }

    @PostMapping("/mailAttach/id/{id}")
    public ResponseEntity<?> sendAttachEmailById(@PathVariable int id, @RequestParam("image") List<MultipartFile> multipartFile, @RequestHeader HttpHeaders httpHeaders) throws IOException, MessagingException {
        imageWriterFacade.sendAttachEmailById(id, multipartFile, httpHeaders);
        return ResponseEntity.ok("Check mail");
    }

    @PostMapping("/deleteFile/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        imageWriterFacade.deleteFile(id);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(imageReaderFacade.findById(id));
    }

    @PostMapping("/findFiles")
    public ResponseEntity<?> findFiles() {
        return ResponseEntity.ok(imageReaderFacade.findFiles());
    }

    @PostMapping("/test/hello")
    public ResponseEntity<?> testHello() {
        return ResponseEntity.ok("testHello");
    }

    @PostMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(imageReaderFacade.findByName(name));
    }

    @PostMapping("/updateFile")
    public ResponseEntity<?> updateFile(@RequestParam("id") Long id
            , @RequestParam("name") String name) {
        imageWriterFacade.updateFile(id, name);
        return ResponseEntity.ok("Đã updated");
    }

}

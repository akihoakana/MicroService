package com.example.StatusAndMess8085.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(name = "UploadFeignClient"
        , url = "${myHostService.uploadImage8082}")
//        , configuration = {FeignSupportConfig.class})
public interface UploadFeignClient {
    @PostMapping(value = "/status/uploadImageTest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String fileUpload(@RequestPart(name = "image") List<MultipartFile> multipartFiles
            , @RequestParam("idUser") int idUser);

}


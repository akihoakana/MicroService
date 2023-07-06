package com.example.UploadFile8082.facade;

import com.example.UploadFile8082.entity.FileDataEntity;
import com.example.UploadFile8082.repository.StorageRepository;
import com.example.UploadFile8082.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class ImageReaderFacade {
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageRepository storageRepository;

    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;

    @Value("${myDetailService.springJPA8081.findByEmailId}")
    private String urlDetailFindByEmailId;

    private RestTemplate restTemplate = new RestTemplate();

    public List<FileDataEntity> uploadImageToFIleSystem(List<MultipartFile> file) throws IOException {
        return storageService.uploadManyImageToFileSystem(file);
    }

    public Optional<FileDataEntity> findById(long id) {
        return storageRepository.findById(id);
    }

    public List<FileDataEntity> findFiles() {
        return storageRepository.findAll();
    }

    public Optional<FileDataEntity> findByName(String name) {
        return storageRepository.findByName(name);
    }
}

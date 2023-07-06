package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.entity.StatusEntity;
import com.example.StatusAndMess8085.repository.StatusRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UsersRepository usersRepository;

    public StatusEntity addStatus(String detail, int id) {
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setDetail(detail);
        statusEntity.setCreated(new Date(System.currentTimeMillis()));
        if (usersRepository.findById(id).isPresent()){
            statusEntity.setUsersEntity(usersRepository.findById(id).get());
            return statusRepository.save(statusEntity);
        }
        else return null;
    }
//    public StatusEntity addStatusWithImage(String detail, int id, List<MultipartFile> multipartFile){
//        StatusEntity statusEntity = new StatusEntity();
//        statusEntity.setDetail(detail);
//        statusEntity.setCreated(new Date(System.currentTimeMillis()));
//        statusEntity.setUsersEntity(usersRepository.findById(id).get());
//       return statusRepository.save(statusEntity);
//    }
}

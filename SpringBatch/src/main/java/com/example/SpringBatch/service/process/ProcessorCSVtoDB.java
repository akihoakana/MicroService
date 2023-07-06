package com.example.SpringBatch.service.process;

import com.example.SpringBatch.DTO.ReadCSVDTO;
import com.example.SpringBatch.DTO.UserDTOBatch;
import com.example.SpringBatch.entity.UsersEntity;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ProcessorCSVtoDB extends ItemListenerSupport<ReadCSVDTO, UsersEntity> implements ItemProcessor<ReadCSVDTO, UsersEntity> {

    public UsersEntity process(ReadCSVDTO readCSVDTO) {
        System.out.println("ProcessorCSVtoDB..." + readCSVDTO.getEmail());
        UsersEntity usersEntity =new UsersEntity();
        usersEntity.setEmail(readCSVDTO.getEmail());
        usersEntity.setFullname(readCSVDTO.getFullname());
        usersEntity.setUsername(readCSVDTO.getUsername());
        usersEntity.setPassword(readCSVDTO.getPassword());
        return usersEntity;
    }
}

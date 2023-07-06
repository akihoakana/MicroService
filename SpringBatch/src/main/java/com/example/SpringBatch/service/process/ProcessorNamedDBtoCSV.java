package com.example.SpringBatch.service.process;

import com.example.SpringBatch.DTO.UserDTOBatch;
import com.example.SpringBatch.DTO.UserEntityBatch;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorNamedDBtoCSV implements ItemProcessor<UserEntityBatch, UserDTOBatch> {

    public UserDTOBatch process(UserEntityBatch userEntityBatch) {
        System.out.println("ProcessorNamedDBtoCSV..." + userEntityBatch.getEmail());
        UserDTOBatch userDTOBatch =new UserDTOBatch();
        userDTOBatch.setId(userEntityBatch.getId());
        userDTOBatch.setEmail(userEntityBatch.getEmail());
        userDTOBatch.setFullname(userEntityBatch.getFullname());
        userDTOBatch.setUsername(userEntityBatch.getUsername());
        return userDTOBatch;
    }

}

package com.example.SpringBatch8084.service.process;


import com.example.SpringBatch8084.DTO.UserDTOBatch;
import com.example.SpringBatch8084.DTO.UserDTOBatch1;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorCSVtoCSV extends ItemListenerSupport<UserDTOBatch, UserDTOBatch1> implements ItemProcessor<UserDTOBatch, UserDTOBatch1> {

    public UserDTOBatch1 process(UserDTOBatch userDTOBatch) {
        System.out.println("ProcessorCSVtoCSV..." + userDTOBatch.getEmail());
        UserDTOBatch1 userDTOBatch1 = new UserDTOBatch1();
        userDTOBatch1.setFullname(userDTOBatch.getFullname());
        userDTOBatch1.setEmail(userDTOBatch.getEmail());
        userDTOBatch1.setUsername(userDTOBatch.getUsername());
        return userDTOBatch1;
    }
}

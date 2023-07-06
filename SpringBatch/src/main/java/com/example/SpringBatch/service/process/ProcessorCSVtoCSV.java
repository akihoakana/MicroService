package com.example.SpringBatch.service.process;


import com.example.SpringBatch.DTO.ReadCSVDTO;
import com.example.SpringBatch.DTO.UserDTOBatch1;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorCSVtoCSV extends ItemListenerSupport<ReadCSVDTO, UserDTOBatch1> implements ItemProcessor<ReadCSVDTO, UserDTOBatch1> {

    public UserDTOBatch1 process(ReadCSVDTO userDTOBatch) {
        System.out.println("ProcessorCSVtoCSV..." + userDTOBatch.getEmail());
        UserDTOBatch1 userDTOBatch1 =new UserDTOBatch1();
        userDTOBatch1.setFullname(userDTOBatch.getFullname());
        userDTOBatch1.setEmail(userDTOBatch.getEmail());
        userDTOBatch1.setUsername(userDTOBatch.getUsername());
        return userDTOBatch1;
    }
}

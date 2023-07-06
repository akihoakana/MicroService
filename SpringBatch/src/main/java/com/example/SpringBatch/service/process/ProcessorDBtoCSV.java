package com.example.SpringBatch.service.process;

import com.example.SpringBatch.DTO.UserDTOBatch;
import com.example.SpringBatch.entity.UsersEntity;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorDBtoCSV extends ItemListenerSupport<UsersEntity, UserDTOBatch> implements ItemProcessor<UsersEntity, UserDTOBatch> {

    private StepExecution stepExecution;

    @Override
    public UserDTOBatch process(UsersEntity usersEntity) {
        System.out.println("ProcessorDBtoCSV..." + usersEntity.getEmail());
        UserDTOBatch userDTOBatch =new UserDTOBatch();
        userDTOBatch.setId(usersEntity.getId());
        userDTOBatch.setEmail(usersEntity.getEmail());
        userDTOBatch.setFullname(usersEntity.getFullname());
        userDTOBatch.setUsername(usersEntity.getUsername());
        return userDTOBatch;
    }
    @Override
    public void beforeProcess(UsersEntity usersEntity) {
        System.out.println("Start ProcessorDBtoCSV...");

    }
//    @Override
//    public void afterProcess(UsersEntity usersEntity,UserDTOBatch userDTOBatch) {
//        if (usersEntity.getId()==4) {
//            stepExecution.setExitStatus(ExitStatus.EXECUTING);
//        }
//    }
}

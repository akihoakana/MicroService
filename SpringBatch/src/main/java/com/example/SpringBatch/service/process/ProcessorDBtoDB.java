package com.example.SpringBatch.service.process;

import com.example.SpringBatch.entity.RolesEntity;
import com.example.SpringBatch.entity.UsersEntity;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorDBtoDB implements ItemProcessor<UsersEntity, RolesEntity> {

    public RolesEntity process(UsersEntity usersEntity) {
        System.out.println("ProcessorDBtoDB..." + usersEntity.getEmail());
        RolesEntity rolesEntity =new RolesEntity();
        rolesEntity.setDescription(usersEntity.getFullname());
        rolesEntity.setName(usersEntity.getEmail());
        return rolesEntity;
    }
}

package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.DTO.MessDTO;
import com.example.StatusAndMess8085.entity.MessEntity;
import com.example.StatusAndMess8085.entity.UsersEntity;
import com.example.StatusAndMess8085.repository.MessRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessServiceTest {

    @Mock
    private MessRepository messRepository;
    @Mock
    private UsersRepository usersRepository;

    @Test
    void addMess() {
        Optional<UsersEntity> usersEntity = Optional.of(new UsersEntity());
        usersEntity.get().setId(3);
        usersEntity.get().setEmail("aaa@gmail.com");

        when(usersRepository.findById(Mockito.anyInt())).thenReturn(usersEntity);

        assertEquals(usersEntity, usersRepository.findById(anyInt()));

        Mockito.verify(usersRepository).findById(anyInt());

        MessEntity messEntity = new MessEntity();
        messEntity.setMessDetail("1211");
        messEntity.setFromUser(usersRepository.findById(anyInt()).get());
        messEntity.setToUser(usersRepository.findById(anyInt()).get());
        messEntity.setTime(new Date(System.currentTimeMillis()));

        when(messRepository.save(messEntity)).thenReturn(messEntity);

        assertEquals(messEntity, messRepository.save(messEntity));

        Mockito.verify(messRepository).save(messEntity);
    }

    @Test
    void fowardMess() {
        Optional<UsersEntity> usersEntity = Optional.of(new UsersEntity());
        usersEntity.get().setId(3);
        usersEntity.get().setEmail("aaa@gmail.com");

        when(usersRepository.findById(Mockito.anyInt())).thenReturn(usersEntity);

        assertEquals(usersEntity, usersRepository.findById(anyInt()));

        Mockito.verify(usersRepository).findById(anyInt());

        Optional<MessEntity> messEntity1 = Optional.of(new MessEntity());
        messEntity1.get().setId(123);
        messEntity1.get().setMessDetail("123");
        messEntity1.get().setTime(new Date(System.currentTimeMillis()));

        when(messRepository.findById(anyInt())).thenReturn(messEntity1);
        assertEquals(messRepository.findById(anyInt()), messEntity1);

        Mockito.verify(messRepository).findById(anyInt());

        MessEntity messEntity = new MessEntity();
        messEntity.setFromUser(usersRepository.findById(anyInt()).get());
        messEntity.setToUser(usersRepository.findById(anyInt()).get());
        messEntity.setMessDetail(messRepository.findById(anyInt()).get().getMessDetail());
        messEntity.setTime(new Date(System.currentTimeMillis()));

        when(messRepository.save(messEntity)).thenReturn(messEntity);

        assertEquals(messEntity, messRepository.save(messEntity));

        Mockito.verify(messRepository).save(messEntity);
    }

    @Test
    void getMessDTOByUsers() {
        List<MessDTO> messDTO = new ArrayList<>();
        List<MessEntity> messEntities = Arrays.asList(new MessEntity(), new MessEntity());

        messRepository.findByFromUserAndToUser(
                usersRepository.findById(anyInt())
                , usersRepository.findById(anyInt()));

        System.out.println("messEntities.size() = " + messEntities.size());
        messEntities.stream()
                .filter(messEntity -> messEntity.getTime() != null)
                .peek(messEntity -> System.out.println("messEntity.getTime() = " + messEntity.getTime()))
                .sorted(Comparator.comparing(MessEntity::getTime))
                .collect(Collectors.toList())
                .forEach(messEntity -> {
                    MessDTO messDTO1 = new MessDTO();
                    messDTO1.setDetail(messEntity.getMessDetail());
                    messDTO1.setNameUser(messEntity.getFromUser().getEmail());
                    messDTO1.setTimeSend(messEntity.getTime());
                    messDTO.add(messDTO1);
                });
    }
}
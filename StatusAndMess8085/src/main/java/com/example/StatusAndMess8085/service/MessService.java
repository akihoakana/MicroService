package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.DTO.MessDTO;
import com.example.StatusAndMess8085.entity.MessEntity;
import com.example.StatusAndMess8085.repository.MessRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessService {

    @Autowired
    private MessRepository messRepository;
    @Autowired
    private UsersRepository usersRepository;

    public MessEntity addMess(int fromUserId, int toUserId, String detail) {
        MessEntity messEntity = new MessEntity();
        messEntity.setMessDetail(detail);
        if (usersRepository.findById(fromUserId).isPresent() && usersRepository.findById(toUserId).isPresent()){
            messEntity.setFromUser(usersRepository.findById(fromUserId).get());
            messEntity.setToUser(usersRepository.findById(toUserId).get());
            messEntity.setTime(new Date(System.currentTimeMillis()));
            return messRepository.save(messEntity);

        }
        else return null;
    }

    public MessEntity fowardMess(int fromUserId, int messId, int fowardUser) {
        MessEntity messEntity = new MessEntity();
        if (usersRepository.findById(fromUserId).isPresent() && usersRepository.findById(fowardUser).isPresent()){
            messEntity.setFromUser(usersRepository.findById(fromUserId).get());
            messEntity.setToUser(usersRepository.findById(fowardUser).get());
            messEntity.setMessDetail(messRepository.findById(messId).get().getMessDetail());
            messEntity.setTime(new Date(System.currentTimeMillis()));
            return messRepository.save(messEntity);
        }
        else return null;
    }

    public List<MessDTO> getMessDTOByUsers(int userId1, int userId2) {
        List<MessDTO> messDTO = new ArrayList<>();
        List<MessEntity> messEntities = new ArrayList<>();
            messEntities.addAll(messRepository.findByFromUserAndToUser(
                    usersRepository.findById(userId1)
                    , usersRepository.findById(userId2)));
            messEntities.addAll(messRepository.findByFromUserAndToUser(
                    usersRepository.findById(userId2)
                    , usersRepository.findById(userId1)));
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
            System.out.println("messDTO.size() = " + messDTO.size());
            return messDTO;

    }
}

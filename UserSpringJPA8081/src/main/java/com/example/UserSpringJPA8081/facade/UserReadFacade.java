package com.example.UserSpringJPA8081.facade;

import com.example.UserSpringJPA8081.DTO.NamedDTO;
import com.example.UserSpringJPA8081.DTO.UserDTO;
import com.example.UserSpringJPA8081.DTO.UserEmailDTO;
import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import com.example.UserSpringJPA8081.payload.response.DataResponse;
import com.example.UserSpringJPA8081.payload.response.RegisterResponse;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import com.example.UserSpringJPA8081.service.RegisterService;
import com.example.UserSpringJPA8081.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserReadFacade {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RegisterService registerService;


    public List<UserEmailDTO> getDataWithUserEmailDTO() {
        List<UserEmailDTO> userEmailDTOS = new ArrayList<>();
        usersRepository.findAll()
                .forEach(usersEntity -> {
                    UserEmailDTO userEmailDTO = new UserEmailDTO();
                    userEmailDTO.setId(usersEntity.getId());
                    userEmailDTO.setEmail(usersEntity.getEmail());
                    userEmailDTOS.add(userEmailDTO);
                });
        return userEmailDTOS;
    }

    public List<UserDTO> usersDTO() {
        List<UserDTO> userDTOS = new ArrayList<>();
        usersRepository.findAll().forEach(usersEntity -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(usersEntity.getId());
            userDTO.setEmail(usersEntity.getEmail());
            userDTO.setPassword(usersEntity.getPassword());
            userDTO.setUsername(usersEntity.getUsername());
            userDTO.setVerify_active(usersEntity.isVerify_active());
            userDTOS.add(userDTO);
        });
        return userDTOS;
    }

    public List<UsersEntity> getUsers() {
        return usersRepository.findAll();
    }

    public Optional<UsersEntity> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<UsersEntity> findById(int id) {
        return usersRepository.findById(id);
    }

    public String findEmailById(int id) {
        return usersRepository.findById(id).get().getEmail();
    }

    public List<NamedDTO> selectUser() {
        return usersRepository.selectUser();
    }

    public UsersEntity updateUserByUser(UsersEntity usersEntity) {
        return usersService.updateUserByUser(usersEntity);
    }

    public List<DataResponse> addListUser(List<SignInRequest> signInRequest, HttpServletRequest request) {
        List<DataResponse> dataResponses = new ArrayList<>();
        signInRequest.forEach(signInRequest1 -> {
            String verifyURL = request.getRequestURL().toString()
                    .replace(request.getServletPath(), "")
                    .replace("8081", "8080") + "/register/verify/" + signInRequest1.getEmail();
            UsersEntity usersEntity = null;
            try {
                usersEntity = registerService.registerNewUserAccount(signInRequest1, verifyURL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            DataResponse dataResponse = new DataResponse();
            dataResponse.setStatus(HttpStatus.OK.value());
            dataResponse.setSuccess(true);
            dataResponse.setDesc("");
            if (usersEntity == null) {
                System.out.println("There is an account with that email address: "
                        + signInRequest1.getEmail());
                dataResponse.setData("There is an account with that email address: "
                        + signInRequest1.getEmail());
            } else {
                RegisterResponse registerResponseModel = new RegisterResponse();
                registerResponseModel.setUsersEntity(usersEntity);
                registerResponseModel.setLinkActive(verifyURL);
                dataResponse.setData(registerResponseModel);
            }
            dataResponses.add(dataResponse);
        });
        return dataResponses;
    }

}

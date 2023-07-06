package com.example.SpringBatch8084.service.process;


import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.example.SpringBatch8084.service.HelloService;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public class ProcessorSaveDBByApi extends ItemListenerSupport<SignInRequest, SignInRequest> implements ItemProcessor<SignInRequest, SignInRequest> {

    @Autowired
    HelloService helloService;
    @Autowired
    RestTemplate restTemplate;

    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;
    @Value("${myDetailService.springJPA8081.addUserGetString}")
    private String urlDetailAddUserGetString;

    @Override
    public SignInRequest process(SignInRequest signInRequest) throws UnsupportedEncodingException, MessagingException {
        System.out.println("signInRequest.getEmail() = " + signInRequest.getEmail());
        System.out.println(urlHostSpringJPA8081 + urlDetailAddUserGetString);
        String rest = restTemplate
                .postForObject(urlHostSpringJPA8081 + urlDetailAddUserGetString
                        , signInRequest, String.class);
        System.out.println("rest = " + rest);
        return signInRequest;
    }
}

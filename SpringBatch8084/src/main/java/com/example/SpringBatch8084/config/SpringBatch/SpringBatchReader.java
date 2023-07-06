package com.example.SpringBatch8084.config.SpringBatch;

import com.example.SpringBatch8084.DTO.UserDTOBatch;
import com.example.SpringBatch8084.DTO.UserEmailDTO;
import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchReader {
    //    @Autowired
//    public BatchController batchController;
    @Autowired
    public RestTemplate restTemplate;

    @Value("${myFile.inputData1}")
    private String inputData1;
    @Value("${myFile.inputData2}")
    private String inputData2;
    @Value("${myFile.inputData3}")
    private String inputData3;
    @Value("${myFile.inputSaveDB}")
    private String inputSaveDB;
    @Value("${myMail.test}")
    private String mailTest;
    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;
    @Value("${myDetailService.springJPA8081.getDataWithUserEmailDTO}")
    private String urlDetailGetDataWithUserEmailDTO;

    @Bean
    @StepScope
    public List<UserEmailDTO> getApiToTakeData() {
        System.out.println("urlSpringJPA8081 = " + urlHostSpringJPA8081);
        System.out.println("urlGetDataWithUserEmailDTO = " + urlDetailGetDataWithUserEmailDTO);
        JsonNode accounts = restTemplate
                .exchange(urlHostSpringJPA8081 + urlDetailGetDataWithUserEmailDTO
                        , HttpMethod.POST
                        , new HttpEntity<>(new HttpHeaders())
                        , JsonNode.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<UserEmailDTO> usersDTOS = mapper.convertValue(accounts, new TypeReference<>() {
        });
        usersDTOS.forEach(userEmailDTO -> System.out.println("userEmailDTO.getEmail() = " + userEmailDTO.getEmail()));
        return usersDTOS.stream().limit(5).collect(Collectors.toList());
    }

    @Bean
    @StepScope
    public List<UserEmailDTO> userEmailDTOExample() {
        List<UserEmailDTO> userEmailDTOS = new ArrayList<>();
        UserEmailDTO userEmailDTO = new UserEmailDTO();
        userEmailDTO.setId(12);
        userEmailDTO.setEmail(mailTest);
        userEmailDTOS.add(userEmailDTO);
        System.out.println("userEmailDTOS.size() = " + userEmailDTOS.size());
        return userEmailDTOS;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<UserDTOBatch> readerCSVByInput1() {
        System.out.println("readerCSV");
        System.out.println("input1 = " + inputData1);
        FlatFileItemReader<UserDTOBatch> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(inputData1));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<SignInRequest> readerCSVByInputSaveDB() {
        System.out.println("readerCSVByInputSaveDB");
        System.out.println("inputSaveDB = " + inputSaveDB);
        FlatFileItemReader<SignInRequest> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(inputSaveDB));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapperByInputSaveDB());
        return itemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<UserDTOBatch> readerCSVByInput3() {
        System.out.println("readerCSV");
        System.out.println("inputData3 = " + inputData3);
        FlatFileItemReader<UserDTOBatch> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(inputData3));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<SignInRequest> lineMapperByInputSaveDB() {
        DefaultLineMapper<SignInRequest> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("email", "password");
        BeanWrapperFieldSetMapper<SignInRequest> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(SignInRequest.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    private LineMapper<UserDTOBatch> lineMapper() {
        DefaultLineMapper<UserDTOBatch> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("email", "username", "fullname", "password");
        BeanWrapperFieldSetMapper<UserDTOBatch> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserDTOBatch.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
//    @Bean
//    @StepScope
//    public RepositoryItemReader<UsersEntity> readerDBtoCSV() throws Exception {
//        Map<String, SpringDataWebProperties.Sort.Direction> sorts = new HashMap<>();
//        sorts.put("id", Sort.Direction.ASC);
//        return new RepositoryItemReaderBuilder<UsersEntity>()
//                .repository(usersRepository)
//                .methodName("findAll")
////                    .arguments(1)
//                .sorts(sorts)
//                .saveState(false)
//                .build();
//    }
//    @Bean
//    @StepScope
//    public RepositoryItemReader<UserEntityBatch> readerNamedDBtoCSV() throws Exception {
//        System.out.println("Reader");
//        Map<String, Sort.Direction> sorts = new HashMap<>();
//        sorts.put("id", Sort.Direction.ASC);
//
//        return new RepositoryItemReaderBuilder<UserEntityBatch>()
//                .repository(usersRepository)
//                .methodName("selectUserDTOBatch")
////                .arguments(pageable,pageable1)
//                .sorts(sorts)
//                .saveState(false)
//                .build();
//    }

}
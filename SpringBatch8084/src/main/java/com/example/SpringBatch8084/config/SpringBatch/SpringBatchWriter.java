package com.example.SpringBatch8084.config.SpringBatch;

import com.example.SpringBatch8084.DTO.UserDTOBatch1;
import com.example.SpringBatch8084.DTO.UserEmailDTO;
import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.example.SpringBatch8084.service.HelloService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchWriter {
    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    HelloService helloService;
    @Value("${myFile.output1}")
    private String output1;
    @Value("${myFile.output2}")
    private String output2;

    @Bean
    @StepScope
    public FlatFileItemWriter<UserDTOBatch1> writerCSVUserDTOBatch1() {
        System.out.println("writerCSV");
        System.out.println(helloService.helloTrackTimeAOP());
        System.out.println("output1 = " + output1);
        FlatFileItemWriter<UserDTOBatch1> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(output1));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[]{"email", "username", "fullname"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<UserEmailDTO> writerCSVUserEmailDTO() {
        System.out.println("writerCSVUserEmailDTO");
        System.out.println(helloService.helloTrackTimeAOP());
        System.out.println("output2 = " + output2);
        FlatFileItemWriter<UserEmailDTO> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(output2));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[]{"id", "email"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public ItemWriter<SignInRequest> writeSignInRequestNull() {
        return new ItemWriter<SignInRequest>() {
            @Override
            public void write(List<? extends SignInRequest> list) throws Exception {

            }
        };
    }

    @Bean
    public ItemWriter<UserEmailDTO> writeNull() {
        return new ItemWriter<UserEmailDTO>() {
            @Override
            public void write(List<? extends UserEmailDTO> list) throws Exception {
                System.out.println("list.size() = " + list.size());
            }
        };
    }
//    @Bean
//    @StepScope
//    public MimeMessageItemWriter writerMail() {
//        System.out.println("writerCSVUserEmailDTO");
//        MimeMessageItemWriter mimeMessageItemWriter= new MimeMessageItemWriter();
//        mimeMessageItemWriter.setJavaMailSender(helloService.sendVerificationEmail());
//        return mimeMessageItemWriter;
//    }
//    @Bean
//    @StepScope
//        public RepositoryItemWriter<UsersEntity> writerDB() {
//        return new RepositoryItemWriterBuilder<UsersEntity>()
//                .repository(usersRepository)
//                .methodName("save")
//                .build();
//    }

}
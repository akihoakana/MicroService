package com.example.SpringBatch.service;

import com.example.SpringBatch.DTO.UserDTOBatch1;
import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.repository.UsersRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class WriterService {


    @Autowired
    private UsersRepository usersRepository;
    @Bean
    public RepositoryItemWriter<UsersEntity> writerCSVtoDB() {
        return new RepositoryItemWriterBuilder<UsersEntity>()
                .repository(usersRepository)
                .methodName("save")
                .build();
    }
    public FlatFileItemWriter<UserDTOBatch1> writerCSV()
    {
        System.out.println("writer");
        FlatFileItemWriter<UserDTOBatch1> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("src/main/resources/output1.csv"));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[] { "email", "username", "fullname"});
                    }

                });
            }
        });
        return writer;
    }
}

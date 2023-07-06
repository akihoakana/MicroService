package com.example.SpringBatch.service;

import com.example.SpringBatch.DTO.ReadCSVDTO;
import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.repository.UsersRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {


    @Autowired
    private UsersRepository usersRepository;

    public FlatFileItemReader<ReadCSVDTO> readerCSVtoDB() {

        FlatFileItemReader<ReadCSVDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/inputData2.csv"));
//        itemReader.open(new ExecutionContext());
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<ReadCSVDTO> lineMapper() {
        DefaultLineMapper<ReadCSVDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("email", "username", "fullname","password");
        BeanWrapperFieldSetMapper<ReadCSVDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ReadCSVDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}

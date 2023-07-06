package com.example.SpringBatch.config;

import com.example.SpringBatch.DTO.ReadCSVDTO;
import com.example.SpringBatch.DTO.UserDTOBatch;
import com.example.SpringBatch.DTO.UserDTOBatch1;
import com.example.SpringBatch.DTO.UserEntityBatch;
import com.example.SpringBatch.entity.RolesEntity;
import com.example.SpringBatch.entity.UsersEntity;
import com.example.SpringBatch.service.ReaderService;
import com.example.SpringBatch.service.WriterService;
import com.example.SpringBatch.service.process.*;
import com.example.SpringBatch.repository.RolesRepository;
import com.example.SpringBatch.repository.UsersRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private UsersRepository usersRepository;
    @Autowired
    @Lazy
    private RolesRepository rolesRepository;

    /**
     * DB to CSV
     * <p>
     * method jpa
     * <p>
     * named naite query
     */
    @Bean
    public RepositoryItemReader<UsersEntity> readerDBtoCSV() throws Exception {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        return new RepositoryItemReaderBuilder<UsersEntity>()
                .repository(usersRepository)
                .methodName("findAll")
//                    .arguments(1)
                .sorts(sorts)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<UserEntityBatch> readerNamedDBtoCSV() throws Exception {
        System.out.println("Reader");
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        return new RepositoryItemReaderBuilder<UserEntityBatch>()
                .repository(usersRepository)
                .methodName("selectUserDTOBatch")
//                .arguments(pageable,pageable1)
                .sorts(sorts)
                .saveState(false)
                .build();
    }

    @Bean
    public FlatFileItemWriter<UserDTOBatch> writerDBtoCSV() {
        System.out.println("writer");

        //Create writer instance
        FlatFileItemWriter<UserDTOBatch> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(new FileSystemResource("src/main/resources/output1.csv"));
//        writer.setShouldDeleteIfEmpty(true);
//        writer.setShouldDeleteIfExists(true);
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[]{"id", "email", "username", "fullname"});
                    }

                });
            }
        });
        return writer;
    }

    /**
     * DB this Table to DB that Table
     */
    @Bean
    public RepositoryItemReader<UsersEntity> readerDBtoDB() {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        return new RepositoryItemReaderBuilder<UsersEntity>()
                .repository(usersRepository)
                .methodName("findAll")
//                .arguments(1)
                .sorts(sorts)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<RolesEntity> writerDBtoDB() {
        return new RepositoryItemWriterBuilder<RolesEntity>()
                .repository(rolesRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public RepositoryItemWriter<UsersEntity> writerMailtoDB() {
        System.out.println("hello writermailtoDB");
        return new RepositoryItemWriterBuilder<UsersEntity>()
                .repository(usersRepository)
                .methodName("save")
//                .methodName("saveAll")
                .build();
    }


    @Bean
    public Step stepDBtoDB() throws Exception {
        return this.stepBuilderFactory.get("DBtoDB")
                .<UsersEntity, RolesEntity>chunk(2)
                .reader(readerDBtoDB())
                .processor(new ProcessorDBtoDB())
                .writer(writerDBtoDB())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Autowired
    WriterService writerService;
    @Autowired
    ReaderService readerService;

//    @Bean
//    public MultiResourceItemReader<ReadCSVDTO> multiResourceItemReaderCSVtoDB() {
//        MultiResourceItemReader<ReadCSVDTO> resourceItemReader = new MultiResourceItemReader<>();
//        Resource resource1 = new FileSystemResource("src/main/resources/inputData3.csv");
//        Resource resource2 = new FileSystemResource("src/main/resources/inputData1.csv");
//        resourceItemReader.setResources(new Resource[]{resource1, resource2});
//        resourceItemReader.setDelegate(readerService.readerCSVtoDB());
//        return resourceItemReader;
//    }

    @Bean
    public Step stepCSVtoDB() throws Exception {
        return this.stepBuilderFactory.get("CSVtoDB")
                .<ReadCSVDTO, UsersEntity>chunk(2)
                .reader(readerService.readerCSVtoDB())
                .processor(new ProcessorCSVtoDB())
                .writer(writerService.writerCSVtoDB())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public Step stepCSVtoCSV() throws Exception {
        return this.stepBuilderFactory.get("CSVtoDB")
                .<ReadCSVDTO, UserDTOBatch1>chunk(2)
                .reader(readerService.readerCSVtoDB())
                .processor(new ProcessorCSVtoCSV())
                .writer(writerService.writerCSV())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepDBtoCSV() throws Exception {
        return this.stepBuilderFactory.get("DBtoCSV")
                .<UsersEntity, UserDTOBatch>chunk(2)
                .reader(readerDBtoCSV())
                .processor(new ProcessorDBtoCSV())
                .writer(writerDBtoCSV())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepNamedDBtoCSV() throws Exception {
        return this.stepBuilderFactory.get("NamedDBtoCSV")
                .<UserEntityBatch, UserDTOBatch>chunk(2)
                .reader(readerNamedDBtoCSV())
                .processor(new ProcessorNamedDBtoCSV())
                .writer(writerDBtoCSV())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepMailToDB() throws Exception {
        return this.stepBuilderFactory.get("stepMailToDB")
                .<UsersEntity, UsersEntity>chunk(1)
                .reader(readerDBtoCSV())
                .processor(new ProcessorSendMail())
                .writer(writerMailtoDB())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step retryStep() throws Exception {
        return this.stepBuilderFactory.get("retryStep")
                .<UsersEntity, UsersEntity>chunk(1)
                .reader(readerDBtoCSV())
                .processor(new ProcessorRetry())
                .writer(writerMailtoDB())
                .faultTolerant()
                .retryLimit(3)
//                .retry(ConnectTimeoutException.class)
                .retry(DeadlockLoserDataAccessException.class)
                .build();
    }
//    @Bean
//    public Step skippingStep( ) throws Exception {
//        return this.stepBuilderFactory.get("skippingStep")
//                .<UsersEntity, UsersEntity>chunk(1)
//                .reader(readerDBtoCSV())
//                .processor(new ProcessorRetry())
//                .writer(writerMailtoDB())
//                .faultTolerant()
//                .skipLimit(2)
//                .skip(MissingUsernameException.class)
//                .skip(NegativeAmountException.class)
//                .build();
//    }

    @Bean
    public Flow flowBuilder() throws Exception {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("notification");
        return flowBuilder.start(stepDBtoDB()).on("COMPLETED").to(stepDBtoCSV())
                .from(stepDBtoCSV()).on("COMPLETED").end().build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(2);
        return asyncTaskExecutor;
    }

    @Bean
    @Qualifier(value = "job1")
    public Job runJob(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("job1")
                .listener(listener)
                .start(flowBuilder()).end().build();
    }

    @Bean
    @Qualifier(value = "mailJob")
    public Job mailJob(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("mailJob")
//                .listener(listener)
                .start(stepMailToDB())
                .build();
    }

    @Bean
    @Qualifier(value = "retryJob")
    public Job retryJob(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("retryJob")
                .listener(listener)
                .start(retryStep())
                .build();
    }

    @Bean
    @Qualifier(value = "jobDBtoCSV")
    public Job jobDBtoCSV(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobDBtoCSV")
                .listener(listener)
                .start(stepDBtoCSV())
//                .on("EXECUTING").to(notificationStep) // điều hướng step tiếp theo
                .build();
    }

    @Bean
    @Qualifier(value = "jobDBtoDB")
    public Job jobDBtoDB(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobDBtoDB")
                .listener(listener)
                .start(stepDBtoDB())
//                .on("EXECUTING").to(notificationStep) // điều hướng step tiếp theo
                .build();
    }

    @Bean
    @Qualifier(value = "jobCSVtoDB")
    public Job jobCSVtoDB(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobCSVtoDB")
                .listener(listener)
                .start(stepCSVtoDB())
//                .on("EXECUTING").to(notificationStep) // điều hướng step tiếp theo
                .build();
    }
    @Bean
    @Qualifier(value = "jobCSVtoCSV")
    public Job jobCSVtoCSV(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobCSVtoCSV")
                .listener(listener)
                .start(stepCSVtoCSV())
//                .on("EXECUTING").to(notificationStep) // điều hướng step tiếp theo
                .build();
    }
}
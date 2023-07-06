package com.example.SpringBatch8084.config.SpringBatch;

import com.example.SpringBatch8084.DTO.UserDTOBatch;
import com.example.SpringBatch8084.DTO.UserDTOBatch1;
import com.example.SpringBatch8084.DTO.UserEmailDTO;
import com.example.SpringBatch8084.payload.request.SignInRequest;
import com.example.SpringBatch8084.service.process.ProcessorCSVtoCSV;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchConfigurationJobAndStep {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public SpringBatchReader springBatchReader;
    @Autowired
    public SpringBatchWriter springBatchWriter;
    @Autowired
    public SpringBatchProcessorInit springBatchProcessorInit;
    @Autowired
    public RestTemplate restTemplate;

    @Bean
    public Step stepCSVByInput1toCSV() throws Exception {
        return this.stepBuilderFactory.get("stepCSVByInput1toCSV")
                .<UserDTOBatch, UserDTOBatch1>chunk(2)
                .reader(springBatchReader.readerCSVByInput1())
//                .reader(readerService.multiResourceItemReaderCSVtoDB())
                .processor(new ProcessorCSVtoCSV())
                .writer(springBatchWriter.writerCSVUserDTOBatch1())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepCSVTest() throws Exception {
//        springBatchReader.readerAPI().forEach(userEmailDTO -> System.out.println("userEmailDTO.getEmail() = " + userEmailDTO.getEmail()));
        return this.stepBuilderFactory.get("stepCSVTest")
                .<UserEmailDTO, UserEmailDTO>chunk(2)
                .reader(new ListItemReader<UserEmailDTO>(springBatchReader.getApiToTakeData()))
                .processor(springBatchProcessorInit.itemProcessor())
//                .writer(springBatchWriter.writerCSVUserEmailDTO())
                .writer(springBatchWriter.writeNull())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepScheduleMail() throws Exception {
        return this.stepBuilderFactory.get("stepScheduleMail")
                .<UserEmailDTO, UserEmailDTO>chunk(2)
                .reader(new ListItemReader<>(springBatchReader.userEmailDTOExample()))
                .processor(springBatchProcessorInit.itemScheduleProcessor())
                .writer(springBatchWriter.writeNull())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepSaveDBByApi() throws Exception {
        return this.stepBuilderFactory.get("stepSaveDBByApi")
                .<SignInRequest, SignInRequest>chunk(2)
                .reader(springBatchReader.readerCSVByInputSaveDB())
                .processor(springBatchProcessorInit.itemProcessor1())
                .writer(springBatchWriter.writeSignInRequestNull())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @Qualifier(value = "jobSaveDBByApi")
    public Job jobSaveDBByApi(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobSaveDBByApi")
                .listener(listener)
                .start(stepSaveDBByApi())
                .build();
    }

    @Bean
    @Qualifier(value = "jobScheduleMail")
    public Job jobScheduleMail(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobScheduleMail")
                .listener(listener)
                .start(stepScheduleMail())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(2);
        return asyncTaskExecutor;
    }

    @Bean
    @Qualifier(value = "jobCSVtoCSV")
    public Job jobCSVtoCSV(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobCSVtoCSV")
                .listener(listener)
                .start(stepCSVByInput1toCSV())
                .build();
    }

    @Bean
    @Qualifier(value = "jobCSVTest")
    public Job jobCSVTest(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory
                .get("jobCSVTest")
                .listener(listener)
                .start(stepCSVTest())
                .build();
    }
//    @Bean
//    public Step stepCSVtoDB( ) throws Exception {
//        return this.stepBuilderFactory.get("CSVtoDB")
//                .<UserDTOBatch, UsersEntity>chunk(2)
//                .reader(new ReaderCSV())
//                .processor(new ProcessorCSVtoDB())
//                .writer(new WriterDB())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Step stepDBtoDB( ) throws Exception {
//        return this.stepBuilderFactory.get("DBtoDB")
//                .<UsersEntity, RolesEntity>chunk(2)
//                .reader(new ReaderDB())
//                .processor(new ProcessorDBtoDB())
//                .writer(new WriterDB())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//    @Bean
//    public Step stepDBtoCSV( ) throws Exception {
//        return this.stepBuilderFactory.get("DBtoCSV")
//                .<UsersEntity, UserDTOBatch>chunk(2)
//                .reader(new ReaderDB())
//                .processor(new ProcessorDBtoCSV())
//                .writer(new WriterCSV())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Step stepNamedDBtoCSV( ) throws Exception {
//        return this.stepBuilderFactory.get("NamedDBtoCSV")
//                .<UserEntityBatch, UserDTOBatch>chunk(2)
//                .reader(new ReaderNamedDBtoCSV())
//                .processor(new ProcessorNamedDBtoCSV())
//                .writer(new WriterCSV())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//    @Bean
//    public Step stepMailToDB( ) throws Exception {
//        return this.stepBuilderFactory.get("mailtoDB")
//                .<UsersEntity, UsersEntity>chunk(1)
//                .reader(new ReaderDB())
//                .processor(new ProcessorSendMail())
//                .writer(new WriterMailtoDB())
//                .taskExecutor(taskExecutor())
//                .build();
//    }

//    @Bean
//    public Flow flowBuilder() throws Exception {
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("notification");
//        return flowBuilder.start(stepDBtoDB()).on("COMPLETED").to(stepDBtoCSV())
//                .from(stepDBtoCSV()).on("COMPLETED").end().build();
//    }

//    @Bean
//    @Qualifier(value = "job1")
//    public Job runJob(JobCompletionNotificationListener listener) throws Exception {
//        return jobBuilderFactory
//                .get("job1")
//                .listener(listener)
//                .start(flowBuilder()).end().build();
//    }
//    @Bean
//    @Qualifier(value = "MailJob")
//    public Job MailJob(JobCompletionNotificationListener listener) throws Exception {
//        return jobBuilderFactory
//                .get("MailJob")
//                .listener(listener)
//                .start(stepMailToDB())
//                .build();
//    }
//    @Bean
//    @Qualifier(value = "job2")
//    public Job runJob1(JobCompletionNotificationListener listener) throws Exception {
//        return jobBuilderFactory
//                .get("job2")
//                .listener(listener)
//                .start(stepDBtoCSV())
////                .on("EXECUTING").to(notificationStep) // điều hướng step tiếp theo
//                .build();
//    }
}
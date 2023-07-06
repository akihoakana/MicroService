package com.example.SpringBatch.controller;

import com.example.SpringBatch.config.SpringBatchConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "/batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;
    @Qualifier("job1")
    @Autowired
    private Job job1;
    @Qualifier("jobCSVtoDB")
    @Autowired
    private Job jobCSVtoDB;
    @Qualifier("jobCSVtoCSV")
    @Autowired
    private Job jobCSVtoCSV;
    @Qualifier("jobDBtoCSV")
    @Autowired
    private Job jobDBtoCSV;
    @Qualifier("jobDBtoDB")
    @Autowired
    private Job jobDBtoDB;
    @Qualifier("mailJob")
    @Autowired
    private Job mailJob;
    @Qualifier("retryJob")
    @Autowired
    private Job retryJob;
    private AtomicInteger batchRunCounter = new AtomicInteger(0);

//    @Autowired
//    private ThreadPoolTaskScheduler scheduler;
    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    private SpringBatchConfiguration springBatchConfiguration;
//    @PostMapping(value = "/stopScheduler")
//    public String stopSchedule(){
//        System.out.println("scheduler.getScheduledExecutor() = " + scheduler.getScheduledExecutor().toString());
//        System.out.println("scheduler.getScheduledExecutor() = " + scheduler.getScheduledThreadPoolExecutor().toString());
////        scheduler.getScheduledExecutor();
//        return "OK";
//    }
//
//    @PostMapping(value = "/startScheduler")
//    public String startSchedule(){
//        postProcessor.postProcessAfterInitialization(scheduledTasks, SCHEDULED_TASKS);
//        return "OK";
//    }

    @PostMapping(value = "/listScheduler")
    public Set<ScheduledTask> listSchedules() throws JsonProcessingException {
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
        if(!setTasks.isEmpty()){
            return setTasks;
        }else{
            return null;
        }
    }
    @PostMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("helloWord I'm Spring Batch");

    }
//    @Scheduled(cron = "0 */5 * * * ?")
    @PostMapping(path = "/retryJob")
    public void retryJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("retryJob", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("retryStep.getName() = " + retryJob.getName());
            jobLauncher.run(retryJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
    private AtomicBoolean enabled = new AtomicBoolean(false);
    private JobExecution jobExecution = null;
    @PostMapping(path = "/enabled")
    public boolean enabled() {
        enabled.set(true);
        return enabled.get();
    }
    @PostMapping(path = "/unenabled")
    public boolean unenabled() {
        enabled.set(false);
        return enabled.get();
    }
//    @Scheduled(cron = "0 */2 * * * ?")
    @PostMapping(path = "/runJob1")
    public void runJob1() {
//        if (StringUtils.hasText(enabled) && enabled.equals("true")) {

        if (enabled.get()) {
            System.out.println("enabled.get() = " + enabled.get());
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            try {
                System.out.println("runJob1.getName() = " + job1.getName());
                jobExecution = jobLauncher.run(job1, jobParameters);
                batchRunCounter.incrementAndGet();

                System.out.println("runJob1 Stopped = " + jobExecution.isStopping());
                System.out.println("runJob1 Stopped = " + jobExecution.isRunning());
                System.out.println("runJob1 Stopped = " + jobExecution.getExitStatus());
                System.out.println("runJob1 Stopped = " + jobExecution.getStatus());
            } catch (JobExecutionAlreadyRunningException | JobRestartException
                    | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                e.printStackTrace();
            }
        } else {
            if (jobExecution !=null) {
                System.out.println("jobExecution !=null");
                jobExecution.setExitStatus(ExitStatus.STOPPED);
//                ScheduledAnnotationBeanPostProcessor bean = context
//                        .getBean(ScheduledAnnotationBeanPostProcessor.class);
//                BatchController schedulerBean = context
//                        .getBean(BatchController.class);
                System.out.println("runJob1 Stopped = " + jobExecution.isStopping());
                System.out.println("runJob1 Stopped = " + jobExecution.isRunning());
                System.out.println("runJob1 Stopped = " + jobExecution.getExitStatus());
                System.out.println("runJob1 Stopped = " + jobExecution.getStatus());
//                jobExecution.setExitStatus(ExitStatus.STOPPED);
                jobExecution.setStatus(BatchStatus.COMPLETED);
            }else {
                System.out.println("runJob1 Stopped = " + job1.getName());
//                System.out.println("jobExecution toString = " + jobExecution.toString());
                }
            }
        }

    // lay email cua table user
    // roi gui mail && chuyen user.issend chuyen bollean va save DB
//    @Scheduled(cron = "0 */2 * * * ?")
    @PostMapping("/mail")
    public ResponseEntity<?> mail(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("MailJob.getName() = " + mailJob.getName());
            jobLauncher.run(mailJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch");
    }
//
    //lay 2 csv input1 & input3 roi save vao table user
    @PostMapping("/jobCSVtoDB")
    public ResponseEntity<?> jobCSVtoDB(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("jobCSVtoDB.getName() = " + jobCSVtoDB.getName());
            jobLauncher.run(jobCSVtoDB, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobCSVtoDB");
    }
    @PostMapping("/jobCSVtoCSV")
    public ResponseEntity<?> jobCSVtoCSV(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("jobCSVtoCSV.getName() = " + jobCSVtoCSV.getName());
            jobLauncher.run(jobCSVtoCSV, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobCSVtoCSV");
    }
    //lay table user save table role
    @PostMapping("/jobDBtoDB")
    public ResponseEntity<?> jobDBtoDB(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("jobDBtoDB.getName() = " + jobDBtoDB.getName());
            jobLauncher.run(jobDBtoDB, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobDBtoDB");
    }
    //lay table user save file output1.csv
    @PostMapping("/jobDBtoCSV")
    public ResponseEntity<?> jobDBtoCSV(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("jobDBtoCSV.getName() = " + jobDBtoCSV.getName());
            jobLauncher.run(jobDBtoCSV, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobDBtoCSV");
    }

//    @PostMapping(path = "/runJob")
//    public void runJob() {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
//        try {
//            System.out.println("runJob.getName() = " + runJob.getName());
//            System.out.println("jobParameters.getParameters() = " + jobParameters.getParameters().toString());
//            JobExecution jobExecution = jobLauncher.run(runJob, jobParameters);
//            System.out.println("jobExecution.toString() = " + jobExecution.toString());
//            System.out.println("jobExecution.getStatus() = " + jobExecution.getStatus());
//            System.out.println("jobExecution.getCreateTime() = " + jobExecution.getCreateTime());
//            System.out.println("jobExecution.getEndTime() = " + jobExecution.getEndTime());
//            System.out.println("jobExecution.getExecutionContext() = " + jobExecution.getExecutionContext());
//            System.out.println("jobExecution.getExitStatus() = " + jobExecution.getExitStatus());
//            System.out.println("jobExecution.getStartTime() = " + jobExecution.getStartTime());
//            System.out.println("jobExecution.getStepExecutions() = " + jobExecution.getStepExecutions());
//            System.out.println("jobExecution.getJobId() = " + jobExecution.getJobId());
//        } catch (JobExecutionAlreadyRunningException | JobRestartException
//                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//    }
}

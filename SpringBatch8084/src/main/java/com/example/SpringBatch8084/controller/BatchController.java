package com.example.SpringBatch8084.controller;

import com.example.SpringBatch8084.config.AOP.TrackTimeAOP;
import com.example.SpringBatch8084.config.Interceptor.SpecifyHeaderInterceptor;
import com.example.SpringBatch8084.config.SpringBatch.JobCompletionNotificationListener;
import com.example.SpringBatch8084.config.SpringBatch.SpringBatchConfigurationJobAndStep;
import com.example.SpringBatch8084.service.HelloService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "/job")
@Profile("clone")
public class BatchController {
    @Value("${school.industry.class.clone}")
    String class1clone;
    @Value("${school.industry.room.clone}")
    String room1clone;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job jobCSVtoCSV;
    @Autowired
    private HelloService helloService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SpringBatchConfigurationJobAndStep springBatchConfigurationJobAndStep;
    private AtomicInteger batchRunCounter = new AtomicInteger(0);

    @SpecifyHeaderInterceptor
    @PostMapping("/yml")
    public ResponseEntity<?> ymlClone() {
        System.out.println("class1clone = " + class1clone);
        System.out.println("room1clone = " + room1clone);
        return ResponseEntity.ok("helloWord I'm Spring Batch yml");
    }

    @TrackTimeAOP
    @PostMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/helloTrackTimeAOP")
    public ResponseEntity<?> helloTrackTimeAOP() {
        System.out.println(helloService.helloTrackTimeAOP());
        return ResponseEntity.ok(helloService.helloTrackTimeAOP());
    }

    @PostMapping("/helloService")
    public ResponseEntity<?> helloService() {
        try {
            helloService.callDaoThrowException();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ResponseEntity.ok("helloWord I'm callDaoFailed");
    }

    @PostMapping("/jobCSVtoCSV")
    public ResponseEntity<?> jobCSVtoCSV() {
//        Map<String, JobParameter> parameters = new HashMap<>();
//        parameters.put("netFileInput", new JobParameter(netFileInput.getFilename()));
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            System.out.println("jobCSVtoCSV.getName() = " + jobCSVtoCSV.getName());
            jobLauncher.run(springBatchConfigurationJobAndStep.jobCSVtoCSV(new JobCompletionNotificationListener()), jobParameters);
//            jobLauncher.run(jobCSVtoCSV, new JobParameters(parameters));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobCSVtoCSV");
    }

    @PostMapping("/jobCSVTest")
    public ResponseEntity<?> jobCSVTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(springBatchConfigurationJobAndStep.jobCSVTest(new JobCompletionNotificationListener()), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobCSVTest");
    }

    //    @Scheduled(cron = "0 */2 * * * ?")
    public ResponseEntity<?> jobScheduleMail() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(springBatchConfigurationJobAndStep.jobScheduleMail(new JobCompletionNotificationListener()), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobScheduleMail");
    }

    @PostMapping("/jobSaveDBByApi")
    public ResponseEntity<?> jobSaveDBByApi() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(springBatchConfigurationJobAndStep.jobSaveDBByApi(new JobCompletionNotificationListener()), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("helloWord I'm Spring Batch jobCSVTest");
    }

////    @Autowired
////    private ThreadPoolTaskScheduler scheduler;
//    @Autowired
//    private ScheduledAnnotationBeanPostProcessor postProcessor;
//
//    @Autowired
//    private SpringBatchConfiguration springBatchConfiguration;
////    @PostMapping(value = "/stopScheduler")
////    public String stopSchedule(){
////        System.out.println("scheduler.getScheduledExecutor() = " + scheduler.getScheduledExecutor().toString());
////        System.out.println("scheduler.getScheduledExecutor() = " + scheduler.getScheduledThreadPoolExecutor().toString());
//////        scheduler.getScheduledExecutor();
////        return "OK";
////    }
////
////    @PostMapping(value = "/startScheduler")
////    public String startSchedule(){
////        postProcessor.postProcessAfterInitialization(scheduledTasks, SCHEDULED_TASKS);
////        return "OK";
////    }
//
//    @PostMapping(value = "/listScheduler")
//    public Set<ScheduledTask> listSchedules() throws JsonProcessingException {
//        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
//        if(!setTasks.isEmpty()){
//            return setTasks;
//        }else{
//            return null;
//        }
//    }
////    @Scheduled(cron = "0 */5 * * * ?")
////    @PostMapping(path = "/retryJob")
////    public void retryJob() {
////        JobParameters jobParameters = new JobParametersBuilder()
////                .addLong("retryJob", System.currentTimeMillis()).toJobParameters();
////        try {
////            System.out.println("retryStep.getName() = " + retryJob.getName());
////            jobLauncher.run(retryJob, jobParameters);
////        } catch (JobExecutionAlreadyRunningException | JobRestartException
////                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
////            e.printStackTrace();
////        }
////    }
//    private AtomicBoolean enabled = new AtomicBoolean(false);
//    private JobExecution jobExecution = null;
//    @PostMapping(path = "/enabled")
//    public boolean enabled() {
//        enabled.set(true);
//        return enabled.get();
//    }
//    @PostMapping(path = "/unenabled")
//    public boolean unenabled() {
//        enabled.set(false);
//        return enabled.get();
//    }
////    @Scheduled(cron = "0 */2 * * * ?")
//    @PostMapping(path = "/runJob1")
//    public void runJob1() {
////        if (StringUtils.hasText(enabled) && enabled.equals("true")) {
//
//        if (enabled.get()) {
//            System.out.println("enabled.get() = " + enabled.get());
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
//            try {
//                System.out.println("runJob1.getName() = " + runJob1.getName());
//                jobExecution = jobLauncher.run(runJob1, jobParameters);
//                batchRunCounter.incrementAndGet();
//
//                System.out.println("runJob1 Stopped = " + jobExecution.isStopping());
//                System.out.println("runJob1 Stopped = " + jobExecution.isRunning());
//                System.out.println("runJob1 Stopped = " + jobExecution.getExitStatus());
//                System.out.println("runJob1 Stopped = " + jobExecution.getStatus());
//            } catch (JobExecutionAlreadyRunningException | JobRestartException
//                    | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
//                e.printStackTrace();
//            }
//        } else {
//            if (jobExecution !=null) {
//                System.out.println("jobExecution !=null");
//                jobExecution.setExitStatus(ExitStatus.STOPPED);
////                ScheduledAnnotationBeanPostProcessor bean = context
////                        .getBean(ScheduledAnnotationBeanPostProcessor.class);
////                BatchController schedulerBean = context
////                        .getBean(BatchController.class);
//                System.out.println("runJob1 Stopped = " + jobExecution.isStopping());
//                System.out.println("runJob1 Stopped = " + jobExecution.isRunning());
//                System.out.println("runJob1 Stopped = " + jobExecution.getExitStatus());
//                System.out.println("runJob1 Stopped = " + jobExecution.getStatus());
////                jobExecution.setExitStatus(ExitStatus.STOPPED);
//                jobExecution.setStatus(BatchStatus.COMPLETED);
//            }else {
//                System.out.println("runJob1 Stopped = " + runJob1.getName());
////                System.out.println("jobExecution toString = " + jobExecution.toString());
//                }
//            }
//        }
//
//    @PostMapping("/mail")
//    public ResponseEntity<?> mail(){
//
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
//        try {
//            System.out.println("MailJob.getName() = " + MailJob.getName());
//            jobLauncher.run(MailJob, jobParameters);
//        } catch (JobExecutionAlreadyRunningException | JobRestartException
//                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok("helloWord I'm Spring Batch");
//    }
//
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

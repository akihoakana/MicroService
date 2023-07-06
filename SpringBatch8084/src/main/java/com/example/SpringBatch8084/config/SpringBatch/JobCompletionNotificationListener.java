package com.example.SpringBatch8084.config.SpringBatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(" Time to verify the results");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");
            System.out.println("!!! JOB FINISHED! Time to verify the results");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(" Time to beforeJob");

        if (jobExecution.getStatus() == BatchStatus.STARTED) {
            LOGGER.info("!!! JOB STARTED! Time to beforeJob");
            System.out.println("!!! JOB STARTED! Time to beforeJob");
        }
    }
}

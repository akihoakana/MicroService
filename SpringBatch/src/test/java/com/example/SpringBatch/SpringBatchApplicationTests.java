package com.example.SpringBatch;

import com.example.SpringBatch.config.SpringBatchConfiguration;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.Collection;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SpringBatchApplicationTests {


	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@After
	public void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
	}

	private JobParameters defaultJobParameters() {
		JobParametersBuilder jobParameters = new JobParametersBuilder();
		return jobParameters.addLong("startAt", System.currentTimeMillis()).toJobParameters();
	}

	@Test
	public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//		JobExecution jobExecution = jobLauncher.run(runJob, jobParameters);

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
		JobInstance actualJobInstance = jobExecution.getJobInstance();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		// then
		assertThat(actualJobInstance.getJobName(), is("job2"));
		assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
	}

	@Test
	public void givenReferenceOutput_whenStep1Executed_thenSuccess() throws Exception {


		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepDBtoCSV", defaultJobParameters());
		Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		// then
		assertThat(actualStepExecutions.size(), is(2));
		assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
	}


}

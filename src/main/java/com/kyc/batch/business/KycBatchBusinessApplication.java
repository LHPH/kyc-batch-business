package com.kyc.batch.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class KycBatchBusinessApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KycBatchBusinessApplication.class);

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private JobRegistry jobRegistry;

    public static void main(String[] args) {
        SpringApplication.run(KycBatchBusinessApplication.class, args);
    }

    @Override
    public void run(String... args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, InvalidJobParametersException {

        if(args.length == 0 ){
            LOGGER.error("Please provided --job argument");
            System.exit(1);
        }

        String value = args[0];
        if(!value.matches("^(--job=)[\\w\\-]{3,100}$")){
            LOGGER.error("Invalid argument format, --job=<JOB_NAME>");
            System.exit(1);
        }

        String jobName = value.replaceFirst("--job=", "");

        Job job = jobRegistry.getJob(jobName);

        LOGGER.info("Job retrieved {}",job);

        if(job == null){
            LOGGER.error("Job {} not found",jobName);
            System.exit(1);
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date-exec",new Date())
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

        LOGGER.info("Launching Job {}",jobName);
        JobExecution execution = jobOperator.start(job, jobParameters);
        LOGGER.info("Result execution of job {}: [{}]",jobName,execution.getStatus());
    }
}

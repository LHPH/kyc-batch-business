package com.kyc.batch.business.config.job.bills;

import com.kyc.core.batch.BatchJobExecutionListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kyc.batch.business.constants.BatchBillConstants.CREATE_CUSTOMER_BILL_JOB_NAME;
import static com.kyc.core.constants.BatchConstants.BATCH_COMPLETED;
import static com.kyc.core.constants.BatchConstants.BATCH_FAILED;

@Configuration
public class CreateCustomerBillJobConfig {

    @Bean
    public Job createCustomerBillJob(
            JobRepository jobRepository,
            Step addCustomerBillStep,
            Step addRelationCustomerServiceAndBillStep){

        return new JobBuilder(CREATE_CUSTOMER_BILL_JOB_NAME,jobRepository)
                .listener(createCustomerBillJobListener())
                .start(addCustomerBillStep).on(BATCH_FAILED).fail()
                .from(addCustomerBillStep).on(BATCH_COMPLETED).to(addRelationCustomerServiceAndBillStep)
                .from(addRelationCustomerServiceAndBillStep).on(BATCH_FAILED).fail()
                .from(addRelationCustomerServiceAndBillStep).on(BATCH_COMPLETED).end()
                .end()
                .build();
    }

    @Bean
    public BatchJobExecutionListener createCustomerBillJobListener(){

        return new BatchJobExecutionListener(CREATE_CUSTOMER_BILL_JOB_NAME);
    }
}

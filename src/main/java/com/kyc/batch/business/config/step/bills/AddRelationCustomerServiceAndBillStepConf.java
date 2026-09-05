package com.kyc.batch.business.config.step.bills;

import com.kyc.batch.business.mappers.bills.RelationCustomerServiceAndBillRowMapper;
import com.kyc.batch.business.model.bills.RelationCustomerServiceAndBill;
import com.kyc.core.batch.BatchStepListener;
import com.kyc.core.properties.KycMessages;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

import static com.kyc.batch.business.constants.BatchBillConstants.ADD_RELATION_CUSTOMER_SERVICE_BILL_STEP;

@Configuration
public class AddRelationCustomerServiceAndBillStepConf {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("queriesProps")
    private Properties queriesProps;

    @Value("${kyc.batch.bills.chunk}")
    private Integer chunkSize;

    @Bean
    public Step addRelationCustomerServiceAndBillStep(JobRepository jobRepository,
                                    PlatformTransactionManager platformTransactionManager,
                                    KycMessages kycMessages

    ){

        return new StepBuilder(ADD_RELATION_CUSTOMER_SERVICE_BILL_STEP,jobRepository)
                .listener(addRelationCustomerServiceBillStepListener(kycMessages))
                .<RelationCustomerServiceAndBill, RelationCustomerServiceAndBill>chunk(chunkSize)
                .transactionManager(platformTransactionManager)
                .reader(relationCustomerServiceAndBillItemReader())
                .writer(relationCustomerServiceAndBillItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<RelationCustomerServiceAndBill> relationCustomerServiceAndBillItemReader(){

        return new JdbcCursorItemReader<>(
                dataSource,
                queriesProps.get("selectRelationCustomerServiceAndBill").toString(),
                new RelationCustomerServiceAndBillRowMapper()
        );
    }

    @Bean
    public JdbcBatchItemWriter<RelationCustomerServiceAndBill> relationCustomerServiceAndBillItemWriter(){

        JdbcBatchItemWriter<RelationCustomerServiceAndBill> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql(queriesProps.get("addRelationCustomerServiceAndBill").toString());
        jdbcBatchItemWriter.setItemPreparedStatementSetter((item,ps)->{
            ps.setLong(1,item.getServiceId());
            ps.setLong(2,item.getBillId());
        });

        return jdbcBatchItemWriter;
    }

    @Bean
    public StepListenerSupport<RelationCustomerServiceAndBill, RelationCustomerServiceAndBill> addRelationCustomerServiceBillStepListener(KycMessages kycMessages){
        return new BatchStepListener<>(ADD_RELATION_CUSTOMER_SERVICE_BILL_STEP,kycMessages.getMessage("001"));
    }
}

package com.kyc.batch.business.config.step;

import com.kyc.batch.business.mappers.bills.CustomerBillMonthDataRowMapper;
import com.kyc.batch.business.model.bills.CustomerBillControlData;
import com.kyc.batch.business.model.bills.CustomerBillMonthData;
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
import java.sql.Date;
import java.util.Properties;

import static com.kyc.batch.business.constants.BatchBillConstants.ADD_CUSTOMER_BILL_STEP;

@Configuration
public class AddCustomerBillStep {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("queriesProps")
    private Properties queriesProps;

    @Value("${kyc.batch.bills.chunk}")
    private Integer chunkSize;

    @Bean
    public Step addCustomerBillStep(JobRepository jobRepository,
                               PlatformTransactionManager platformTransactionManager,
                               KycMessages kycMessages

    ){

        return new StepBuilder(ADD_CUSTOMER_BILL_STEP,jobRepository)
                .listener(addCustomerBillStepListener(kycMessages))
                .<CustomerBillMonthData, CustomerBillControlData>chunk(chunkSize)
                .transactionManager(platformTransactionManager)
                .reader(readCustomerBillWithNoRelation())
                .writer(addCustomerBill())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<CustomerBillMonthData> readCustomerBillWithNoRelation(){

        return new JdbcCursorItemReader<>(
                dataSource,
                queriesProps.get("readCustomerBillWithNoRelation").toString(),
                new CustomerBillMonthDataRowMapper()
        );
    }



    @Bean
    public JdbcBatchItemWriter<CustomerBillControlData> addCustomerBill(){

        JdbcBatchItemWriter<CustomerBillControlData> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql(queriesProps.get("addCustomerBill").toString());
        jdbcBatchItemWriter.setItemPreparedStatementSetter((item,ps)->{

            ps.setDouble(1,item.getTaxes());
            ps.setDouble(2,item.getSubtotal());
            ps.setDouble(3,item.getTotal());
            ps.setLong(4,item.getCustomerId());
            ps.setBoolean(5,item.isSettled());
            ps.setInt(6,item.getIdStatus());
            ps.setDate(7, Date.valueOf(item.getIssueDate().toLocalDate()));
            ps.setDate(8,Date.valueOf(item.getBillingStartDate()));
            ps.setDate(9,Date.valueOf(item.getBillingFinishDate()));
            ps.setDate(10,Date.valueOf(item.getPaymentDueDate().toLocalDate()));
        });

        return jdbcBatchItemWriter;
    }

    @Bean
    public StepListenerSupport<CustomerBillMonthData, CustomerBillControlData> addCustomerBillStepListener(KycMessages kycMessages){
        return new BatchStepListener<>(ADD_CUSTOMER_BILL_STEP,kycMessages.getMessage("001"));
    }
}

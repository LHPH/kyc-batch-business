package com.kyc.batch.business.config;

import com.kyc.core.properties.KycMessages;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {KycMessages.class})
@Configuration
@EntityScan(basePackages = {"com.kyc.batch.postalcodes.entity","com.kyc.core.persistence.entity"})
public class CommonConfig {

}

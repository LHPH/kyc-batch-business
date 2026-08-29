package com.kyc.batch.business.mappers.bills;

import com.kyc.batch.business.model.bills.CustomerBillMonthData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerBillMonthDataRowMapper implements RowMapper<CustomerBillMonthData> {

    @Override
    public CustomerBillMonthData mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("ID");
        Long customerId = rs.getLong("ID_CUSTOMER");
        Double total = rs.getDouble("TOTAL_SERVICE_COST");

        return new CustomerBillMonthData(id, customerId, total);
    }
}

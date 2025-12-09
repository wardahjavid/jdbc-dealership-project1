package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaseDao {
    private final DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (VIN, lease_start, lease_end, monthly_payment) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contract.getVin());
            statement.setDate(2, java.sql.Date.valueOf(contract.getLeaseStart()));
            statement.setDate(3, java.sql.Date.valueOf(contract.getLeaseEnd()));
            statement.setDouble(4, contract.getMonthlyPayment());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting lease contract: " + e.getMessage());
        }
    }
}

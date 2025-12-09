package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesDao {
    private final DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (VIN, sale_date, price) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contract.getVin());
            statement.setDate(2, java.sql.Date.valueOf(contract.getSaleDate()));
            statement.setDouble(3, contract.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting sales contract: " + e.getMessage());
        }
    }
}

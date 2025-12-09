package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private final DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        String sql = "INSERT INTO inventory (dealership_id, VIN) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);
            statement.setString(2, vin);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding vehicle to inventory: " + e.getMessage());
        }
    }

    public void removeVehicleFromInventory(String vin) {
        String sql = "DELETE FROM inventory WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vin);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error removing vehicle from inventory: " + e.getMessage());
        }
    }
}

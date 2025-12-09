package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private final DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle v) {
        String sql = """
                INSERT INTO vehicles (VIN, make, model, year, SOLD, color, vehicleType, odometer, price)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, v.getVin());
            statement.setString(2, v.getMake());
            statement.setString(3, v.getModel());
            statement.setInt(4, v.getYear());
            statement.setBoolean(5, v.isSold());
            statement.setString(6, v.getColor());
            statement.setString(7, v.getVehicleType());
            statement.setInt(8, v.getOdometer());
            statement.setDouble(9, v.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }

    public void removeVehicle(String vin) {
        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vin);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error removing vehicle: " + e.getMessage());
        }
    }

    // ---------- SEARCH METHODS ---------- //

    public List<Vehicle> searchByPriceRange(double min, double max) {
        String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        return search(sql, ps -> {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
        });
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        String sql = "SELECT * FROM vehicles WHERE make = ? AND model = ?";
        return search(sql, ps -> {
            ps.setString(1, make);
            ps.setString(2, model);
        });
    }

    public List<Vehicle> searchByYearRange(int min, int max) {
        String sql = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";
        return search(sql, ps -> {
            ps.setInt(1, min);
            ps.setInt(2, max);
        });
    }

    public List<Vehicle> searchByColor(String color) {
        String sql = "SELECT * FROM vehicles WHERE color = ?";
        return search(sql, ps -> ps.setString(1, color));
    }

    public List<Vehicle> searchByMileageRange(int min, int max) {
        String sql = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        return search(sql, ps -> {
            ps.setInt(1, min);
            ps.setInt(2, max);
        });
    }

    public List<Vehicle> searchByType(String type) {
        String sql = "SELECT * FROM vehicles WHERE vehicleType = ?";
        return search(sql, ps -> ps.setString(1, type));
    }

    // ---------- SHARED SEARCH LOGIC ---------- //

    private List<Vehicle> search(String sql, SqlParameterSetter setter) {
        List<Vehicle> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setter.setParameters(statement);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(createVehicleFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }

        return results;
    }

    private Vehicle createVehicleFromResultSet(ResultSet rs) throws SQLException {
        Vehicle v = new Vehicle();
        v.setVin(rs.getString("VIN"));
        v.setMake(rs.getString("make"));
        v.setModel(rs.getString("model"));
        v.setYear(rs.getInt("year"));
        v.setSold(rs.getBoolean("SOLD"));
        v.setColor(rs.getString("color"));
        v.setVehicleType(rs.getString("vehicleType"));
        v.setOdometer(rs.getInt("odometer"));
        v.setPrice(rs.getDouble("price"));
        return v;
    }

    private interface SqlParameterSetter {
        void setParameters(PreparedStatement ps) throws SQLException;
    }
}

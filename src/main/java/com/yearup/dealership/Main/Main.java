package com.yearup.dealership.Main;

import com.yearup.dealership.db.InventoryDao;
import com.yearup.dealership.db.LeaseDao;
import com.yearup.dealership.db.SalesDao;
import com.yearup.dealership.db.VehicleDao;
import com.yearup.dealership.models.LeaseContract;
import com.yearup.dealership.models.SalesContract;
import com.yearup.dealership.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        // --------------------------------------------
        // FIX: Hardcoded MySQL credentials
        // --------------------------------------------
        String username = "root";
        String password = "yearup";

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/car_dealership");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        VehicleDao vehicleDao = new VehicleDao(dataSource);
        InventoryDao inventoryDao = new InventoryDao(dataSource);
        SalesDao salesDao = new SalesDao(dataSource);
        LeaseDao leaseDao = new LeaseDao(dataSource);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Search vehicles");
            System.out.println("2. Add a vehicle");
            System.out.println("3. Add a contract");
            System.out.println("4. Remove a vehicle");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1 -> searchVehiclesMenu(vehicleDao, scanner);
                case 2 -> addVehicleMenu(vehicleDao, inventoryDao, scanner);
                case 3 -> addContractMenu(salesDao, leaseDao, scanner);
                case 4 -> removeVehicleMenu(vehicleDao, inventoryDao, scanner);
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting program...");
    }

    // ---------------------- CONTRACT MENU ---------------------- //

    private static void addContractMenu(SalesDao salesDao, LeaseDao leaseDao, Scanner scanner) {
        System.out.print("Enter the VIN of the vehicle: ");
        String vin = scanner.nextLine();

        System.out.println("\nSelect a contract type:");
        System.out.println("1. Sales Contract");
        System.out.println("2. Lease Contract");
        System.out.print("Enter your choice: ");

        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> addSalesContract(salesDao, vin, scanner);
            case 2 -> addLeaseContract(leaseDao, vin, scanner);
            default -> System.out.println("Invalid contract type.");
        }
    }

    private static void addSalesContract(SalesDao salesDao, String vin, Scanner scanner) {
        System.out.print("Enter the sale date (YYYY-MM-DD): ");
        LocalDate saleDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter the price: ");
        double price = getDoubleInput(scanner);

        SalesContract contract = new SalesContract(vin, saleDate, price);
        salesDao.addSalesContract(contract);

        System.out.println("Sales contract added successfully.");
    }

    private static void addLeaseContract(LeaseDao leaseDao, String vin, Scanner scanner) {
        System.out.print("Enter lease start date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter lease end date (YYYY-MM-DD): ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter the monthly payment: ");
        double monthlyPayment = getDoubleInput(scanner);

        LeaseContract contract = new LeaseContract(vin, start, end, monthlyPayment);
        leaseDao.addLeaseContract(contract);

        System.out.println("Lease contract added successfully.");
    }

    // ---------------------- SEARCH MENU ---------------------- //

    private static void searchVehiclesMenu(VehicleDao vehicleDao, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n========== SEARCH VEHICLES ==========");
            System.out.println("1. By price range");
            System.out.println("2. By make/model");
            System.out.println("3. By year range");
            System.out.println("4. By color");
            System.out.println("5. By mileage range");
            System.out.println("6. By type");
            System.out.println("7. Back");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1 -> searchByPriceRange(vehicleDao, scanner);
                case 2 -> searchByMakeAndModel(vehicleDao, scanner);
                case 3 -> searchByYearRange(vehicleDao, scanner);
                case 4 -> searchByColor(vehicleDao, scanner);
                case 5 -> searchByMileageRange(vehicleDao, scanner);
                case 6 -> searchByType(vehicleDao, scanner);
                case 7 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void searchByPriceRange(VehicleDao dao, Scanner scanner) {
        System.out.print("Min price: ");
        double min = getDoubleInput(scanner);

        System.out.print("Max price: ");
        double max = getDoubleInput(scanner);

        displaySearchResults(dao.searchByPriceRange(min, max));
    }

    private static void searchByMakeAndModel(VehicleDao dao, Scanner scanner) {
        System.out.print("Make: ");
        String make = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        displaySearchResults(dao.searchByMakeModel(make, model));
    }

    private static void searchByYearRange(VehicleDao dao, Scanner scanner) {
        System.out.print("Min year: ");
        int min = getIntInput(scanner);

        System.out.print("Max year: ");
        int max = getIntInput(scanner);

        displaySearchResults(dao.searchByYearRange(min, max));
    }

    private static void searchByColor(VehicleDao dao, Scanner scanner) {
        System.out.print("Color: ");
        String color = scanner.nextLine();

        displaySearchResults(dao.searchByColor(color));
    }

    private static void searchByMileageRange(VehicleDao dao, Scanner scanner) {
        System.out.print("Min mileage: ");
        int min = getIntInput(scanner);

        System.out.print("Max mileage: ");
        int max = getIntInput(scanner);

        displaySearchResults(dao.searchByMileageRange(min, max));
    }

    private static void searchByType(VehicleDao dao, Scanner scanner) {
        System.out.print("Type: ");
        String type = scanner.nextLine();

        displaySearchResults(dao.searchByType(type));
    }

    private static void displaySearchResults(List<Vehicle> vehicles) {
        System.out.println("\n========== SEARCH RESULTS ==========");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            vehicles.forEach(System.out::println);
        }
    }

    // ---------------------- VEHICLE MANAGEMENT ---------------------- //

    private static void addVehicleMenu(VehicleDao dao, InventoryDao inventoryDao, Scanner scanner) {

        String vin = generateRandomVin();

        System.out.print("Make: ");
        String make = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Year: ");
        int year = getIntInput(scanner);

        System.out.print("Color: ");
        String color = scanner.nextLine();

        System.out.print("Mileage: ");
        int mileage = getIntInput(scanner);

        System.out.print("Price: ");
        double price = getDoubleInput(scanner);

        System.out.print("Type: ");
        String type = scanner.nextLine();

        System.out.print("Dealership ID: ");
        int dealershipId = getIntInput(scanner);

        Vehicle v = new Vehicle(vin, make, model, year, false, color, type, mileage, price);

        dao.addVehicle(v);
        inventoryDao.addVehicleToInventory(vin, dealershipId);

        System.out.println("Vehicle added successfully with VIN: " + vin);
    }

    private static void removeVehicleMenu(VehicleDao dao, InventoryDao inventoryDao, Scanner scanner) {
        System.out.print("Enter VIN to remove: ");
        String vin = scanner.nextLine();

        inventoryDao.removeVehicleFromInventory(vin);
        dao.removeVehicle(vin);

        System.out.println("Vehicle removed successfully.");
    }

    // ---------------------- HELPERS ---------------------- //

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid number. Try again: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static double getDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid number. Try again: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private static String generateRandomVin() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 17).toUpperCase();
    }
}

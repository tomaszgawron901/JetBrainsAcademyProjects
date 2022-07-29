package carsharing.dao;

import carsharing.dto.CarDto;
import carsharing.dto.CompanyDto;
import carsharing.dto.CustomerDto;
import carsharing.tool.ResultSetParser;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CarSharingDeoImp implements CarSharingDao, Closeable {
    private final Connection connection;

    public CarSharingDeoImp(String connectionString) throws NotAbleToConnectException {
        try {
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection(connectionString);
            connection.setAutoCommit(true);
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new NotAbleToConnectException(e);
        }

        try {
            createCompanyTable();
            createCarTable();
            createCustomerTable();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //region Create tables
    private void createCompanyTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS COMPANY" +
                "(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL" +
                ")"
        );
        statement.close();
    }

    private void createCarTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
            "CREATE TABLE IF NOT EXISTS CAR" +
                "(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL," +
                    "COMPANY_ID INT NOT NULL," +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ")"
        );
        statement.close();
    }

    private void createCustomerTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS CUSTOMER" +
                     "(" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME VARCHAR UNIQUE NOT NULL," +
                        "RENTED_CAR_ID INT," +
                        "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                     ")"
        );
        statement.close();
    }
    //endregion

    //region Company
    public Optional<ArrayList<CompanyDto>> getCompanyList() {
        try(
            var statement = connection.prepareStatement("SELECT * FROM COMPANY");
            var resultSet = statement.executeQuery()
        ) {
            return ResultSetParser.parseList(resultSet, (rs) -> {
                var id = rs.getInt("ID");
                var name = rs.getString("NAME");
                return new CompanyDto(id, name);
            });
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }

    public boolean addCompany(String name) {
        try(
            var statement = connection.prepareStatement("INSERT INTO COMPANY (NAME) VALUES (?)")
        ) {
            statement.setString(1, name);
            statement.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Optional<Boolean> isCompanyListEmpty() {
        try (
            var statement = connection.prepareStatement("SELECT EXISTS(select * from COMPANY)");
            var resultSet = statement.executeQuery()
        ) {
            return ResultSetParser.parse(resultSet, rs -> {
                try {
                    return !rs.getBoolean(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CompanyDto> getCompanyById(int companyId) {
        try (
                var statement = connection
                        .prepareStatement("SELECT TOP 1 * FROM COMPANY WHERE ID = ?")
        ) {
            statement.setInt(1, companyId);
            try (var resultSet = statement.executeQuery()) {
                return ResultSetParser.parse(resultSet, (rs) -> {
                    var id = rs.getInt("ID");
                    var name = rs.getString("NAME");
                    return new CompanyDto(id, name);
                });
            }
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }
    //endregion

    //region Car
    public Optional<ArrayList<CarDto>> getCarListByCompanyId(int companyId) {
        try(
                var statement = connection.prepareStatement(
                            "SELECT * "+ 
                                "FROM CAR " + 
                                "WHERE COMPANY_ID = " + companyId
                );
                var resultSet = statement.executeQuery()
        ) {
            return ResultSetParser.parseList(resultSet, (rs) -> new CarDto(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getInt("COMPANY_ID")
                )
            );
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<CarDto>> getAvailableCarListByCompanyId(int companyId) {
        try(
            var statement = connection.prepareStatement(
                    "SELECT *  FROM CAR  WHERE COMPANY_ID = ? AND ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL )"
            )
        ) {
            statement.setInt(1, companyId);
            try (var resultSet = statement.executeQuery()) {
                return ResultSetParser.parseList(resultSet, (rs) -> new CarDto(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("COMPANY_ID")
                ));
            }
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }

    public boolean addCar(CarDto car) {
        if (car == null) {
            throw new IllegalArgumentException("Car is null");
        }

        try(
                var statement = connection.prepareStatement("INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)")
        ) {
            statement.setString(1, car.getName());
            statement.setInt(2, car.getCompanyId());
            statement.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    //endregion

    //region Customer
    public Optional<ArrayList<CustomerDto>> getCustomerList() {
        try(
                var statement = connection.prepareStatement("SELECT * FROM CUSTOMER");
                var resultSet = statement.executeQuery()
        ) {
            return ResultSetParser.parseList(resultSet, (rs) -> {
                var id = rs.getInt("ID");
                var name = rs.getString("NAME");
                var rentedCarId = rs.getInt("RENTED_CAR_ID");
                return new CustomerDto(id, name, rentedCarId);
            });
        }
        catch (SQLException e) {
            return Optional.empty();
        }
    }

    public boolean addCustomer(String name) {
        try(
                var statement = connection.prepareStatement("INSERT INTO CUSTOMER (NAME) VALUES (?)")
        ) {
            statement.setString(1, name);
            statement.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<CarDto> getRentedCarByCustomerId(int customerId) {
        try(
                var statement = connection.prepareStatement("SELECT * FROM CAR WHERE ID = (SELECT TOP 1 RENTED_CAR_ID FROM CUSTOMER WHERE ID = ?)")
        ) {
            statement.setInt(1, customerId);
            try(var result = statement.executeQuery()) {
                return ResultSetParser.parse(result, (rs) -> new CarDto(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("COMPANY_ID")
                ));
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean returnCustomerCar(int customerId) {
        try(
                var statement = connection.prepareStatement("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?")
        ) {
            statement.setInt(1, customerId);
            statement.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean rentCar(int customerId, int carId) {
        try(
                var statement = connection.prepareStatement("UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?")
        ) {
            statement.setInt(1, carId);
            statement.setInt(2, customerId);
            statement.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<CustomerDto> getCustomerById(int customerId) {
        try(
                    var statement = connection.prepareStatement("SELECT TOP 1 * FROM CUSTOMER WHERE ID = ?")
        ) {
            statement.setInt(1, customerId);
            try(var result = statement.executeQuery()) {
                return ResultSetParser.parse(result, (rs) -> new CustomerDto(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("RENTED_CAR_ID")
                ));
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }
    //endregion

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

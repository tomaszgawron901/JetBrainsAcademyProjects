package carsharing.dao;

import carsharing.dto.CarDto;
import carsharing.dto.CompanyDto;
import carsharing.dto.CustomerDto;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Optional;

public interface CarSharingDao extends Closeable {
    //region Company
    Optional<ArrayList<CompanyDto>> getCompanyList();
    boolean addCompany(String name);
    Optional<Boolean> isCompanyListEmpty();
    Optional<CompanyDto> getCompanyById(int companyId);
    //endregion

    //region Car
    Optional<ArrayList<CarDto>> getCarListByCompanyId(int companyId);
    Optional<ArrayList<CarDto>> getAvailableCarListByCompanyId(int companyId);
    boolean addCar(CarDto car);
    //endregion

    //region Customer
    Optional<ArrayList<CustomerDto>> getCustomerList();
    boolean addCustomer(String name);
    Optional<CarDto> getRentedCarByCustomerId(int customerId);
    boolean returnCustomerCar(int customerId);
    boolean rentCar(int customerId, int carId);
    Optional<CustomerDto> getCustomerById(int customerId);
    //endregion
}

package carsharing;

import carsharing.dao.CarSharingDao;
import carsharing.dto.CarDto;
import carsharing.dto.CompanyDto;
import carsharing.function.MenuAction;
import carsharing.menu.StateMenu;
import carsharing.menu.base_menu.CustomerMenu;
import carsharing.menu.list_menu.CarListMenu;
import carsharing.menu.list_menu.CompanyListMenu;
import carsharing.menu.base_menu.CompanyMenu;
import carsharing.menu.base_menu.ManagerMenu;
import carsharing.menu.base_menu.StartMenu;
import carsharing.menu.list_menu.CustomerListMenu;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MenuController {
    private final StartMenu startMenu;
    private final ManagerMenu managerMenu;
    private final CompanyListMenu companyListMenu;
    private final CompanyMenu companyMenu;
    private final CustomerListMenu customerListMenu;
    private final CustomerMenu customerMenu;
    private final CompanyListMenu companyListForRentingCarMenu;
    private final CarListMenu companyAvailableCarListMenu;

    private final Scanner scanner;
    private final PrintStream printStream;
    private final CarSharingDao carSharingDao;

    protected final StateMenu startingMenu;

    public MenuController(CarSharingDao carSharingDao, InputStream inputStream, OutputStream outputStream) {
        scanner = new Scanner(inputStream);
        printStream = new PrintStream(outputStream);
        startMenu = new StartMenu();
        managerMenu = new ManagerMenu();
        companyListMenu = new CompanyListMenu();
        companyMenu = new CompanyMenu();
        customerListMenu = new CustomerListMenu();
        customerMenu = new CustomerMenu();
        companyListForRentingCarMenu = new CompanyListMenu();
        companyAvailableCarListMenu = new CarListMenu();

        startingMenu = startMenu;

        this.carSharingDao = carSharingDao;

        configureStartMenu();
        configureManagerMenu();
        configureCompanyListMenu();
        configureCompanyMenu();
        configureCustomerListMenu();
        configureCustomerMenu();
        configureCompanyListForRentingCarMenu();
        configureCompanyAvailableCarListMenu();
    }

    private void configureStartMenu() {
        startMenu.setOnLoginAsManager(() -> managerMenu);
        startMenu.setOnCreateCustomer(() -> {
            printStream.println("Enter the customer name:");
            var name = scanner.nextLine();
            if(carSharingDao.addCustomer(name)) {
                printStream.println("The customer was added!");
            }
            else {
                printStream.println("The customer was not added!");
            }
            printStream.println();
            return startMenu;
        });
        startMenu.setOnLoginAsCustomer(() -> {
            var customers = carSharingDao.getCustomerList();
            if (customers.isEmpty() || customers.get().size() == 0) {
                printStream.println("The customer list is empty!");
                printStream.println();
                return startMenu;
            }
            customerListMenu.setItemList(customers.get());
            return customerListMenu;
        });
        startMenu.setOnExit(MenuAction.Empty);
    }

    private void configureManagerMenu() {
        managerMenu.SetOnShowCompanyList(() -> {
            var companyList = carSharingDao.getCompanyList();
            if (companyList.isEmpty() || companyList.get().size() == 0) {
                printStream.println("The company list is empty");
                printStream.println();
                return managerMenu;
            }
            companyListMenu.setItemList(companyList.get());
            return companyListMenu;
        });
        managerMenu.setOnCreateCompany(() -> {
            printStream.println("Enter the company name:");
            var name = scanner.nextLine();
            if(carSharingDao.addCompany(name)) {
                printStream.println("The company was created!");
            }
            printStream.println();
            return managerMenu;
        });
        managerMenu.setOnBack(MenuAction.of(startMenu));
    }

    private void configureCompanyListMenu() {
        companyListMenu.setOnItemChoose((company) -> {
            companyMenu.setCompanyDto(company);
            return companyMenu;
        });
        companyListMenu.setOnBack(MenuAction.of(managerMenu));
    }

    private void configureCompanyMenu() {
        companyMenu.setOnShowCarList(() -> {
            var carListOptional = carSharingDao.getCarListByCompanyId(companyMenu.getCompanyDto().getId());
            if (carListOptional.isEmpty() || carListOptional.get().isEmpty()) {
                printStream.println("The car list is empty!");
                printStream.println();
                return companyMenu;
            }
            printStream.println("Car list:");
            var carList = carListOptional.get();
            IntStream.rangeClosed(1, carList.size())
                    .sequential()
                    .forEach(i -> printStream.println(i + ". " + carList.get(i-1).getName()));
            printStream.println();
            return companyMenu;
        });
        companyMenu.setOnCreateCar(() -> {
            printStream.println("Enter the car name:");
            var name = scanner.nextLine();
            if(carSharingDao.addCar(new CarDto(0, name, companyMenu.getCompanyDto().getId()))) {
                printStream.println("The car was added!");
            }
            else {
                printStream.println("The car was not added!");
            }
            printStream.println();
            return companyMenu;
        });
        companyMenu.setOnBack(MenuAction.of(managerMenu));
    }

    private void configureCustomerListMenu() {
        customerListMenu.setOnItemChoose((customer) -> {
            customerMenu.setCustomer(customer);
            return customerMenu;
        });
        customerListMenu.setOnBack(MenuAction.of(startMenu));
    }

    private void configureCustomerMenu() {
        customerMenu.setOnRentACar(() -> {
            if(customerMenu.getCustomer().getRentedCarId() != 0) {
                printStream.println("You've already rented a car!");
                printStream.println();
                return customerMenu;
            }

            var companyList = carSharingDao.getCompanyList();
            if (companyList.isEmpty() || companyList.get().size() == 0) {
                printStream.println("The company list is empty!");
                printStream.println();
                return customerMenu;
            }

            companyListForRentingCarMenu.setItemList(companyList.get());
            return companyListForRentingCarMenu;
        });
        customerMenu.setOnReturnACar(() -> {
            Optional<CarDto> rentedCarOptional = carSharingDao.getRentedCarByCustomerId(customerMenu.getCustomer().getId());
            if(rentedCarOptional.isEmpty()) {
                printStream.println("You didn't rent a car!");
                printStream.println();
                return customerMenu;
            }
            if(carSharingDao.returnCustomerCar(customerMenu.getCustomer().getId())) {
                printStream.println("You've returned a rented car!");
                printStream.println();
            }
            else {
                printStream.println("The car was not returned!");
                printStream.println();
            }
            var updatedCustomer = carSharingDao.getCustomerById(customerMenu.getCustomer().getId());
            updatedCustomer.ifPresent(customerMenu::setCustomer);
            return customerMenu;
        });
        customerMenu.setOnShowMyRentedCar(() -> {
            Optional<CarDto> rentedCarOptional = carSharingDao.getRentedCarByCustomerId(customerMenu.getCustomer().getId());
            if(rentedCarOptional.isEmpty()) {
                printStream.println("You didn't rent a car!");
                printStream.println();
                return customerMenu;
            }
            printStream.println("You rented a car:");
            printStream.println(rentedCarOptional.get().getName());
            Optional<CompanyDto> companyOptional = carSharingDao.getCompanyById(rentedCarOptional.get().getCompanyId());
            printStream.println("Company:");
            if(companyOptional.isEmpty()) {
                printStream.println("The company is not found!");
                printStream.println();
            }
            else {
                printStream.println(companyOptional.get().getName());
                printStream.println();
            }
            return customerMenu;
        });
        customerMenu.setOnBack(MenuAction.of(startMenu));
    }

    private void configureCompanyListForRentingCarMenu() {
        companyListForRentingCarMenu.setOnItemChoose((company) -> {
            var availableCarList = carSharingDao.getAvailableCarListByCompanyId(company.getId());
            if(availableCarList.isEmpty() || availableCarList.get().isEmpty()) {
                printStream.println("No available cars in the '" + company.getName() + "' company");
                printStream.println();
                return customerMenu;
            }
            companyAvailableCarListMenu.setItemList(availableCarList.get());
            return companyAvailableCarListMenu;
        });
        companyListForRentingCarMenu.setOnBack(MenuAction.of(customerMenu));
    }

    private void configureCompanyAvailableCarListMenu() {
        companyAvailableCarListMenu.setOnItemChoose((car) -> {
            if(carSharingDao.rentCar(customerMenu.getCustomer().getId(), car.getId())) {
                printStream.println("You rented '" + car.getName() + "'");
                printStream.println();
            }
            else {
                printStream.println("The car was not rented!");
                printStream.println();
            }
            var updatedCustomer = carSharingDao.getCustomerById(customerMenu.getCustomer().getId());
            updatedCustomer.ifPresent(customerMenu::setCustomer);
            return customerMenu;
        });
        companyAvailableCarListMenu.setOnBack(MenuAction.of(customerMenu));
    }

    public void run() {
        var currentManu = startingMenu;
        while (currentManu != null) {
            printStream.println(currentManu.getMenu());
            var input = scanner.nextLine();
            printStream.println();
            currentManu = currentManu.handleInput(input);
        }
    }



}

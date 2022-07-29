package carsharing.menu.base_menu;

import carsharing.dto.CustomerDto;
import carsharing.function.MenuAction;

public class CustomerMenu extends BaseStateMenu{
    private MenuAction onRentACar;
    private MenuAction onReturnACar;
    private MenuAction onShowMyRentedCar;
    private MenuAction onBack;

    private CustomerDto customer;


    public CustomerMenu() {
        putMenuItem("1", "Rent a car", () -> NullActionHandler(onRentACar));
        putMenuItem("2", "Return a rented car", () -> NullActionHandler(onReturnACar));
        putMenuItem("3", "My rented car", () -> NullActionHandler(onShowMyRentedCar));
        putMenuItem("0", "Back", () -> NullActionHandler(onBack));
    }

    public void setOnRentACar(MenuAction onRentACar) {
        this.onRentACar = onRentACar;
    }

    public void setOnReturnACar(MenuAction onReturnACar) {
        this.onReturnACar = onReturnACar;
    }

    public void setOnShowMyRentedCar(MenuAction onShowMyRentedCar) {
        this.onShowMyRentedCar = onShowMyRentedCar;
    }

    public void setOnBack(MenuAction onBack) {
        this.onBack = onBack;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public CustomerDto getCustomer() {
        return customer;
    }
}

package carsharing.menu.base_menu;

import carsharing.function.MenuAction;

public class StartMenu extends BaseStateMenu {
    private MenuAction onLoginAsManager;
    private MenuAction onLoginAsCustomer;
    private MenuAction onCreateCustomer;
    private MenuAction onExit;

    public StartMenu() {
        putMenuItem("1", "Log in as a manager", () -> NullActionHandler(onLoginAsManager));
        putMenuItem("2", "Log in as a customer", () -> NullActionHandler(onLoginAsCustomer));
        putMenuItem("3", "Create a customer", () -> NullActionHandler(onCreateCustomer));
        putMenuItem("0", "Exit", () -> NullActionHandler(onExit));
    }

    public void setOnLoginAsManager(MenuAction action) {
        this.onLoginAsManager = action;
    }

    public void setOnExit(MenuAction action) {
        this.onExit = action;
    }

    public void setOnLoginAsCustomer(MenuAction onLoginAsCustomer) {
        this.onLoginAsCustomer = onLoginAsCustomer;
    }

    public void setOnCreateCustomer(MenuAction onCreateCustomer) {
        this.onCreateCustomer = onCreateCustomer;
    }
}

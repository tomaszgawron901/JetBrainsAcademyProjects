package carsharing.menu.base_menu;

import carsharing.function.MenuAction;

public class ManagerMenu extends BaseStateMenu {

    private MenuAction onShowCompanyList;
    private MenuAction onCreateCompany;
    private MenuAction onBack;

    public ManagerMenu() {
        putMenuItem("1", "Company list", () -> NullActionHandler(onShowCompanyList));
        putMenuItem("2", "Create a company", () -> NullActionHandler(onCreateCompany));
        putMenuItem("0", "Back", () -> NullActionHandler(onBack));
    }

    public void SetOnShowCompanyList(MenuAction action) {
        this.onShowCompanyList = action;
    }

    public void setOnBack(MenuAction action) {
        this.onBack = action;
    }

    public void setOnCreateCompany(MenuAction action) {
        this.onCreateCompany = action;
    }
}

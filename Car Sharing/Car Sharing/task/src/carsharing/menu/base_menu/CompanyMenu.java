package carsharing.menu.base_menu;

import carsharing.dto.CompanyDto;
import carsharing.function.MenuAction;

public class CompanyMenu extends BaseStateMenu {
    private CompanyDto companyDto;
    private MenuAction onShowCarList;
    private MenuAction onCreateCar;
    private MenuAction onBack;

    public CompanyMenu() {
        putMenuItem("1", "Car list", () -> NullActionHandler(onShowCarList));
        putMenuItem("2", "Create a car", () -> NullActionHandler(onCreateCar));
        putMenuItem("0", "Back", () -> NullActionHandler(onBack));
    }

    @Override
    public String getMenu() {
        return "'" + companyDto.getName() + "' company:\n" + super.getMenu();
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setOnShowCarList(MenuAction onShowCarList) {
        this.onShowCarList = onShowCarList;
    }

    public void setOnCreateCar(MenuAction onCreateCar) {
        this.onCreateCar = onCreateCar;
    }

    public void setOnBack(MenuAction onBack) {
        this.onBack = onBack;
    }

}

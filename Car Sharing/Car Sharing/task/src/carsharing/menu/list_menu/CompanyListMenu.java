package carsharing.menu.list_menu;

import carsharing.dto.CompanyDto;

import java.util.Comparator;

public class CompanyListMenu extends ListStateMenu<CompanyDto> {

    @Override
    protected String getMenuHeader() {
        return "Choose a company:";
    }

    @Override
    protected String getEmptyListMessage() {
        return "Company list is empty!";
    }

    @Override
    protected Comparator<CompanyDto> getComparator() {
        return Comparator.comparing(CompanyDto::getId, Comparator.naturalOrder());
    }

    @Override
    protected String getItemPlaceholder(CompanyDto item) {
        return item == null ? "" : item.getName();
    }
}

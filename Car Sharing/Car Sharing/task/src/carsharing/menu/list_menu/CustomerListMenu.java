package carsharing.menu.list_menu;


import carsharing.dto.CustomerDto;

import java.util.Comparator;

public class CustomerListMenu extends ListStateMenu<CustomerDto> {
    @Override
    protected String getMenuHeader() {
        return "Choose a customer:";
    }

    @Override
    protected String getEmptyListMessage() {
        return "Customer list is empty.";
    }

    @Override
    protected Comparator<CustomerDto> getComparator() {
        return Comparator.comparing(CustomerDto::getId, Comparator.naturalOrder());
    }

    @Override
    protected String getItemPlaceholder(CustomerDto item) {
        return item == null ? "" : item.getName();
    }
}

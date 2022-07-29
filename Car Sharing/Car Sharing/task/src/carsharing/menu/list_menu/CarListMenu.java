package carsharing.menu.list_menu;

import carsharing.dto.CarDto;

import java.util.Comparator;

public class CarListMenu extends ListStateMenu<CarDto> {
    @Override
    protected String getMenuHeader() {
        return "Choose a car:";
    }

    @Override
    protected String getEmptyListMessage() {
        return "The list is empty";
    }

    @Override
    protected Comparator<CarDto> getComparator() {
        return Comparator.comparing(CarDto::getId);
    }

    @Override
    protected String getItemPlaceholder(CarDto item) {
        if (item == null) {
            return "";
        }
        var name = item.getName();
        return name == null ? "" : name;
    }
}

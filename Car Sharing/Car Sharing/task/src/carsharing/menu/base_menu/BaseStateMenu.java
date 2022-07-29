package carsharing.menu.base_menu;

import carsharing.function.MenuAction;
import carsharing.menu.StateMenu;
import carsharing.menu.base_menu.sub_class.MenuItem;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public abstract class BaseStateMenu implements StateMenu {

    protected LinkedHashMap<String, MenuItem> menuItems = new LinkedHashMap<>();

    public String getMenu() {
        return menuItems.entrySet().stream()
                .map(entry -> entry.getKey() + ". " + entry.getValue().getName())
                .collect(Collectors.joining("\n"));
    }

    public StateMenu handleInput(String input) {
        var item = menuItems.get(input);
        return item != null ? item.getAction().get() : this;
    }

    protected StateMenu NullActionHandler(MenuAction action) {
        return action != null ? action.get() : this;
    }

    protected void putMenuItem(String key, String description, MenuAction action) {
        menuItems.put(key, new MenuItem(description, action));
    }
}

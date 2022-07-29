package carsharing.menu;

public interface StateMenu {
    String getMenu();
    StateMenu handleInput(String input);
}

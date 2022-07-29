package carsharing.function;

import carsharing.menu.StateMenu;

import java.util.function.Supplier;

/**
 * Supplier of the state menu.
 */
@FunctionalInterface
public
interface MenuAction extends Supplier<StateMenu> {
    /**
     * @return the next state
     */
    StateMenu get();

    MenuAction Empty = () -> null;

    static MenuAction of(StateMenu state) {
        return () -> state;
    }
}

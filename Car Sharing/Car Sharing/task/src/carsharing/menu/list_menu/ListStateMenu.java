package carsharing.menu.list_menu;

import carsharing.function.MenuAction;
import carsharing.menu.StateMenu;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ListStateMenu<T> implements StateMenu {
    protected Function<T, StateMenu> onItemChoose;
    protected MenuAction onBack;
    protected List<T> itemList;

    protected abstract String getMenuHeader();
    protected abstract String getEmptyListMessage();
    protected abstract Comparator<T> getComparator();
    protected abstract String getItemPlaceholder(T item);


    /**
     * Set action to be performed after user chooses an item.
     * @param action the action takes an item and return the next state.
     */
    public void setOnItemChoose(Function<T, StateMenu> action) {
        this.onItemChoose = action;
    }
    public void setOnBack(MenuAction action) {
        this.onBack = action;
    }

    public void setItemList(Collection<T> itemList) throws IllegalArgumentException {
        if (itemList.isEmpty()) {
            throw new IllegalArgumentException(getEmptyListMessage());
        }

        this.itemList = itemList.stream()
                .sorted(getComparator())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getMenu() {
        if (itemList == null || itemList.isEmpty()) {
            var message = getEmptyListMessage();
            if (message != null) {
                return message + "\n";
            }
            return "";
        }

        return IntStream.rangeClosed(1, itemList.size())
                .sequential()
                .collect(
                        () -> {
                            var header = getMenuHeader();
                            var sb = new StringBuilder();
                            if (header != null) {
                                sb.append(header).append("\n");
                            }
                            return sb;
                        },
                        (sb, i) -> {
                            sb.append(i);
                            sb.append(". ");
                            var placeholder = getItemPlaceholder(itemList.get(i-1));
                            if (placeholder != null) {
                                sb.append(placeholder);
                            }
                            sb.append("\n");
                        },
                        StringBuilder::append
                )
                .append("0. Back")
                .toString();
    }

    @Override
    public StateMenu handleInput(String input) {
        int id;
        try {
            id = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return this;
        }

        if (id == 0 ) {
            return onBack == null ? null : onBack.get();
        }

        if(itemList == null || itemList.isEmpty()) {
            return null;
        }

        if(onItemChoose == null) {
            return null;
        }

        if(id < 1 || id > itemList.size()) {
            return this;
        }

        return onItemChoose.apply(itemList.get(id-1));
    }
}

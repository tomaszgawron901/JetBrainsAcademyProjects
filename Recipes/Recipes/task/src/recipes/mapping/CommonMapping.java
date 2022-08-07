package recipes.mapping;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommonMapping {
    public static <T, R> List<R> mapList(List<T> list, Function<T, R> function) {
        if (list == null) {
            return null;
        }
        return list.stream().map(function).collect(Collectors.toList());
    }
}

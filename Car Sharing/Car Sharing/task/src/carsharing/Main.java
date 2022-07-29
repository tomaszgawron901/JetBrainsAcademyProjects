package carsharing;

import carsharing.dao.CarSharingDao;
import carsharing.dao.CarSharingDeoImp;
import carsharing.dao.NotAbleToConnectException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static String databaseFilePathRoot = "./src/carsharing/db/";
    static Map<String, String> properties;

    static Map<String, String> getProperties(String[] args) {
        var propertiesMap = new HashMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            String potentialKey = args[i];
            if (potentialKey.startsWith("-") && potentialKey.length() > 1) {
                String potentialValue = args[i+1];
                if (!potentialValue.startsWith("-")) {
                    propertiesMap.put(potentialKey.substring(1), potentialValue);
                    i++;
                }
            }
        }
        return propertiesMap;
    }

    public static void main(String[] args) {
        properties = getProperties(args);
        String databaseFileName = properties.getOrDefault("databaseFileName", "carsharing");
        String connectionString = "jdbc:h2:"+ databaseFilePathRoot + databaseFileName;

        try(CarSharingDao dao = new CarSharingDeoImp(connectionString)) {
            MenuController controller = new MenuController(dao, System.in, System.out);
            controller.run();
        }
        catch (NotAbleToConnectException | IOException e) {
            System.out.println("Unable to connect to database");
        }
    }
}
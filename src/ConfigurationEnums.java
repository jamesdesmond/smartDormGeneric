import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by james on 11/9/16.
 */
public class ConfigurationEnums {
    /*
    *showWeather config
    */
    public static final String[] coordinates = {
            "LAT", //LAT
            "LONG" //LONG
    };
    public static String api = "000000000000000000000000000000000"; //api key of length 32

    public enum WeatherInfo {
        TODAY(0),
        HIGH_TEMP(1),
        LOW_TEMP(18);
        private int index;

        WeatherInfo(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public static void readConfigurationFile() {
        try {
            Ini ini = new Ini(new File("Configuration.ini"));
            coordinates[0] = ini.get("showWeather", "LATITUDE");
            coordinates[1] = ini.get("showWeather", "LONGITUDE");
            api = ini.get("showWeather", "API");
            createPeople(ini);
            createMessages(ini);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *sendText config
     */

    public static ArrayList<String> messageArrayList = new ArrayList<>();
    private static void createMessages(Ini ini) {
        messageArrayList = new ArrayList<>(Arrays.asList(ini.get("sendText","MESSAGES").split(",")));
    }
    public static ArrayList<Person> peopleArrayList = new ArrayList<>();
    private static void createPeople(Ini ini) {
        String[] foo = ini.get("sendText","CONTACTS").split(",|:");
        if (foo.length % 2 == 0) {
            for(int i = 0; i < foo.length;i += 2) {
                System.out.println("foo[i]" + foo[i]);
                System.out.println("foo[i+1]" + foo[i+1]);
                peopleArrayList.add(new Person(foo[i],foo[i+1]));
            }
            for (Person p : peopleArrayList) {
                System.out.println(p.getName() + "|" + p.getEmail());
            }
        }
    }
}

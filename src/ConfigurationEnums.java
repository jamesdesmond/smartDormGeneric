import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author James Desmond
 * SmartDormGeneric Final Assignment
 * CS1000-Fall 2016
 * Due: 12/6/16
 *
 * **********************
 * Description
 * **********************
 *
 * Handles reading in of settings from Configuration.ini
 *
 */
public class ConfigurationEnums {
    /**
     * Reads Configuration.ini and sets variables accordingly
     */
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
    *showWeather config
    */
    /**
     * Holds location for weather
     */
    public static final String[] coordinates = {
            "LAT", //LAT
            "LONG" //LONG
    };
    /**
     * API key for the weather API
     */
    public static String api = "000000000000000000000000000000000"; //api key of length 32

    /**
     * Enum for referencing aspects of the weather forecast by name instead of number
     */
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
    /*
     *sendText config
     */
    /**
     * Arraylist of all messages entered into Configuration.ini
     */
    public static ArrayList<String> messageArrayList;

    /**
     * Assigns values of Configuration.ini to messageArrayList
     * @param ini Accesses .ini file
     */
    private static void createMessages(Ini ini) {
        messageArrayList = new ArrayList<>(Arrays.asList(ini.get("sendText","MESSAGES").split(",")));
    }

    /**
     * Arraylist of all people entered into Configuration.ini
     */
    public static ArrayList<Person> peopleArrayList = new ArrayList<>();

    /**
     * Assigns values of Configuration.ini to peopleArrayList
     * @param ini Accesses .ini file
     */
    private static void createPeople(Ini ini) {
        String[] foo = ini.get("sendText","CONTACTS").split(",|:");
        if (foo.length % 2 == 0) {
            for(int i = 0; i < foo.length;i += 2) {
                peopleArrayList.add(new Person(foo[i],foo[i+1]));
            }
        }
    }
}

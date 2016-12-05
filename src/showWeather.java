import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * **********************
 * Description
 * **********************
 *
 * Shows the current weather as well as a 6-day forecast
 *
 * **********************
 * Analysis
 * **********************
 *
 * Inputs: Location via Configuration.ini, api key via Configuration.ini
 * Outputs: LCD
 *
 * **********************
 * Pseudocode
 * **********************
 * 1.Display output of either WeatherMainScreen or WeatherSixDay
 */

public class showWeather implements LCDApps {
    private final WeatherApps[] WEATHER_APPS = new WeatherApps[]{new WeatherMainScreen(), new WeatherSixDay()};
    private int currentMenu;

    /**
     * initializes the menu at 0
     */
    public showWeather() {
        currentMenu = 0;
    }

    /**
     * Handles the menu for showWeather
     * @param ilcd LCD
     * @param button buttonpresses
     * @throws IOException
     */
    private void menu(ILCD ilcd, Button button) throws IOException {
        ilcd.setText(WEATHER_APPS[0].toString());
        try {
            switch (button) {
                case RIGHT:
                    currentMenu++;
                    currentMenu = (currentMenu > WEATHER_APPS.length - 1) ? 0 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(WEATHER_APPS[currentMenu].toString());
                    break;
                case LEFT:
                    currentMenu--;
                    currentMenu = (currentMenu < 0) ? WEATHER_APPS.length - 1 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(WEATHER_APPS[currentMenu].toString());
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets name
     * @return name
     */
    @Override
    public String getName() {
        return "Weather";
    }

    /**
     * Handles button presses
     * @param ilcd LCD
     * @param button buttonpresses
     * @throws IOException
     */
    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
        menu(ilcd, button);
    }

    /**
     * Handles initial run of showWeather()
     * @param ilcd LCD
     * @throws IOException
     */
    public void run(ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Loading...");
        ilcd.setText(WEATHER_APPS[0].toString());
    }

    /**
     *  **********************
     *  Description
     *  **********************
     *
     *  Generates a 6 day forecast
     *
     *  **********************
     *  Analysis
     *  **********************
     *
     *  Inputs:Todays date,api key
     *  Outputs:Six Day forecast
     */
    class WeatherSixDay implements WeatherApps {
        /**
         * Gets name
         * @return name
         */
        @Override
        public String getName() {
            return "6 Day Forecast";
        }

        /**
         * Generates 6 day forecast
         * @return 6 day forecast
         */
        public String toString() {
            String[] daysOfTheWeek = new String[]{"M", "T", "W", "T", "F", "S", "S", "M", "T", "W", "T", "F", "S", "S"};
            String[] dayFirstLetter = new String[6];
            @SuppressWarnings("deprecation") int startDay = Calendar.getInstance().getTime().getDay();
            System.out.println("startDay: " + startDay);
            for (int i = 0; i < 6; i++) {
                dayFirstLetter[i] = daysOfTheWeek[startDay - 1 + i];
            }
            System.out.println(Arrays.toString(dayFirstLetter));

            String api = ConfigurationEnums.api;
            api = api.substring(0, 32);

            System.out.println("api: " + api + "length: " + api.length());
            ForecastIO fio = new ForecastIO(api);
            fio.setUnits(ForecastIO.UNITS_US);             //sets the units as SI - optional
            fio.getForecast(ConfigurationEnums.coordinates[0], ConfigurationEnums.coordinates[1]);
            FIODaily daily = new FIODaily(fio);
            int[] temperatures = new int[6];
            for (int i = 0; i < 6; i++) {
                int todaysTemp = daily.getDay(i + 1).temperatureMax().intValue();
                temperatures[i] = (todaysTemp >= 100) ? 99 : todaysTemp; //Screen is too small to fit values > 99
            }
            String topRow = dayFirstLetter[0] + ":" + temperatures[0] + " " + dayFirstLetter[1] + ":" + temperatures[1] + " " + dayFirstLetter[2] + ":" + temperatures[2] + "\n";
            String bottomRow = dayFirstLetter[3] + ":" + temperatures[3] + " " + dayFirstLetter[4] + ":" + temperatures[4] + " " + dayFirstLetter[5] + ":" + temperatures[5];
            return topRow + bottomRow;
        }
    }

    /**
     * **********************
     * Description
     * **********************
     *
     * Generates a forecast for today using the high, low, precipitation chance, and current temp
     *
     * **********************
     * Analysis
     * **********************
     *
     * Inputs: api
     * Outputs: todays forecast
     *
     * **********************
     * Pseudocode
     * **********************
     *
     * Get todays weather
     * Get the high, low, current temp, and precipitation chance
     * Based on the length of those 4 generate an "offset" string of space characters
     * Combine string
     * return string
     */
    class WeatherMainScreen implements WeatherApps {
        /**
         * Gets name
         * @return name
         */
        @Override
        public String getName() {
            return "Weather Forecast";
        }

        /**
         * Gets todays forecast and generates it to fit a 16x2 LCD
         * @return todays forecast
         */
        public String toString() {
            //Getting api responses
            String api = ConfigurationEnums.api;
            api = api.substring(0, 32);
            ForecastIO fio = new ForecastIO(api);
            fio.setUnits(ForecastIO.UNITS_US);             //sets the units as SI - optional
            fio.getForecast(ConfigurationEnums.coordinates[0], ConfigurationEnums.coordinates[1]);
            FIOCurrently currently = new FIOCurrently(fio);
            FIODaily daily = new FIODaily(fio);
            String[] h = daily.getDay(ConfigurationEnums.WeatherInfo.TODAY.getIndex()).getFieldsArray();
            int hi = (int) Double.parseDouble(daily.getDay(ConfigurationEnums.WeatherInfo.TODAY.getIndex()).getByKey(h[ConfigurationEnums.WeatherInfo.HIGH_TEMP.getIndex()])); //high for the day
            int lo = (int) Double.parseDouble(daily.getDay(ConfigurationEnums.WeatherInfo.TODAY.getIndex()).getByKey(h[ConfigurationEnums.WeatherInfo.LOW_TEMP.getIndex()])); //low for the day
            int temp = currently.get().temperature().intValue();
            int rain = currently.get().precipProbability().intValue();
            //Building output
            String offsetTopRow = "";
            for (int i = 0; i < 3 - String.valueOf(temp).length(); i++) {
                offsetTopRow = offsetTopRow + " ";
            }
            String offsetBottomRow = "";
            rain = rain * 100; //Make into percentage
            for (int i = 0; i < 3 - String.valueOf(rain).length(); i++) {
                offsetBottomRow = offsetBottomRow + " ";
            }
            offsetBottomRow = offsetBottomRow + "";
            return
                    "NOW:" + temp + offsetTopRow + "   " + "HI:" + hi + "\n" +
                            "RAIN:" + rain + "%" + offsetBottomRow + " " + "LO:" + lo;
        }
    }

}

/**
 * Allows for arrays of WeatherApps
 */
interface WeatherApps {
    String getName();
}

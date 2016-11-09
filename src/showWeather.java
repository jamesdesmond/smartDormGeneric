import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by james on 9/24/16.
 */

public class showWeather implements LCDApps {
    private final WeatherApps[] WEATHER_APPS = new WeatherApps[] {new  WeatherMainScreen(),new WeatherSixDay()};
    private int currentMenu;
    public showWeather(){
        currentMenu = 0;
    };
    private void menu(ILCD ilcd,Button button) throws IOException {
        ilcd.setText(WEATHER_APPS[0].toString());
        try {
            switch (button) {
                case RIGHT:
                    //ilcd.clear();
                    currentMenu++;
                    currentMenu = (currentMenu > WEATHER_APPS.length - 1) ? 0 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(WEATHER_APPS[currentMenu].toString());
                    break;
                case LEFT:
                    //ilcd.clear();
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
    @Override
    public String getName() {
        return "Weather";
    }

    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
        menu(ilcd,button);
    }
    public void run(ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Loading...");
        ilcd.setText(WEATHER_APPS[0].toString());
    }
    class WeatherSixDay implements WeatherApps {

        @Override
        public String getName() {
            return "6 Day Forecast";
        }
        public String toString() {
            String[] daysOfTheWeek = new String[] {"M","T","W","T","F","S","S","M","T","W","T","F","S","S"};
            String[] dayFirstLetter = new String[6];
            @SuppressWarnings("deprecation") int startDay = Calendar.getInstance().getTime().getDay();
            System.out.println("startDay: " + startDay);
            for (int i = 0; i < 6; i++) {
                dayFirstLetter[i] = daysOfTheWeek[startDay - 1 + i];
            }
            System.out.println(Arrays.toString(dayFirstLetter));

            String api = Configuration.api;
            api = api.substring(0,32);

            System.out.println("api: " + api + "length: " + api.length());
            ForecastIO fio = new ForecastIO(api);
            fio.setUnits(ForecastIO.UNITS_US);             //sets the units as SI - optional
            fio.getForecast(Configuration.coordinates[0],Configuration.coordinates[1]);
            FIODaily daily = new FIODaily(fio);
            int[] temperatures = new int[6];
            for (int i = 0; i < 6; i ++) {
                int todaysTemp = daily.getDay(i+1).temperatureMax().intValue();
                temperatures[i] = (todaysTemp >= 100)?99:todaysTemp; //Screen is too small to fit values > 99
            }
            String topRow    = dayFirstLetter[0] + ":" + temperatures[0] + " " + dayFirstLetter[1] + ":" + temperatures[1] + " " + dayFirstLetter[2] + ":" + temperatures[2] + "\n";
            String bottomRow = dayFirstLetter[3] + ":" + temperatures[3] + " " + dayFirstLetter[4] + ":" + temperatures[4] + " " + dayFirstLetter[5] + ":" + temperatures[5];
            return topRow + bottomRow;
        }
    }
    class WeatherMainScreen implements WeatherApps {
        @Override
        public String getName() {
            return "Weather Forecast";
        }
        public String toString() {
            //Getting api responses
            String api = Configuration.api;
            api = api.substring(0,32);
            ForecastIO fio = new ForecastIO(api);
            fio.setUnits(ForecastIO.UNITS_US);             //sets the units as SI - optional
            fio.getForecast(Configuration.coordinates[0],Configuration.coordinates[1]);
            FIOCurrently currently = new FIOCurrently(fio);
            FIODaily daily = new FIODaily(fio);
            String [] h = daily.getDay(Configuration.WeatherInfo.TODAY.getIndex()).getFieldsArray();
            int hi = (int) Double.parseDouble(daily.getDay(Configuration.WeatherInfo.TODAY.getIndex()).getByKey(h[Configuration.WeatherInfo.HIGH_TEMP.getIndex()])); //high for the day
            int lo = (int) Double.parseDouble(daily.getDay(Configuration.WeatherInfo.TODAY.getIndex()).getByKey(h[Configuration.WeatherInfo.LOW_TEMP.getIndex()])); //low for the day
            int temp = currently.get().temperature().intValue();
            int rain = currently.get().precipProbability().intValue();
            //Building output
            String offsetTopRow = "";
            for (int i = 0; i < 3 - String.valueOf(temp).length();i++) {
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
 * Created by james on 10/14/16.
 */
interface WeatherApps {
    String getName();
}

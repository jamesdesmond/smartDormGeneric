/**
 * Created by james on 11/9/16.
 */
public class Configuration {
    /*
     *showWeather config
     */
    public static final String[] coordinates = {
            "LAT", //LAT
            "LONG" //LONG
    };
    public static final String api = "000000000000000000000000000000000"; //api key of length 32
    public enum WeatherInfo {
        TODAY (0),
        HIGH_TEMP (1),
        LOW_TEMP (18);
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
    public enum People {
        DEFAULT ("PhoneNumber@Carrier.com");

        People(String email) {
            this.email = email;
        }

        final public String email;
    }


}

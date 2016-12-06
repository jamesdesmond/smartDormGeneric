import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

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
 * Simulates random chance events of a coin flipping or a 6
 * sided die being rolled
 *
 */
public class Random implements LCDApps{
    /**
     * Array of RandomApps to allow for new RandomApps to be added
     */
    private final RandomApps[] RANDOM_APPS = new RandomApps[] {new RollADie(),new FlipACoin()};
    /**
     * Keeps track of the current menu
     */
    private int currentMenu;

    /**
     * Default Constructor
     * Sets currentMenu to -1
     */
    public Random() {
        currentMenu = -1;
    }

    /**
     * Makes a menu for all of the random options
     * @param ilcd LCD
     * @param button buttonpresses
     * @throws IOException
     */
    private void menu(ILCD ilcd,Button button) throws IOException {
        ilcd.setText(RANDOM_APPS[0].toString());
        try {
            switch (button) {
                case RIGHT:
                    currentMenu++;
                    currentMenu = (currentMenu > RANDOM_APPS.length - 1) ? 0 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(RANDOM_APPS[currentMenu].getName());
                    break;
                case LEFT:
                    currentMenu--;
                    currentMenu = (currentMenu < 0) ? RANDOM_APPS.length - 1 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(RANDOM_APPS[currentMenu].getName());
                    break;
                case SELECT:
                    ilcd.clear();
                    ilcd.setText(RANDOM_APPS[currentMenu].run());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getName() {
        return "Random Selection";
    }

    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
        menu(ilcd,button);
    }

    @Override
    public void run(ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Use Right & Left\nto see options");
    }

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
     * Simulates a 2 sided coin being flipped
     */
    class FlipACoin implements RandomApps {
        /**
         * Gets name
         * @return name
         */
        @Override
        public String getName() {
            return "Flip a Coin";
        }

        /**
         * Returns Heads or Tails
         * @return
         */
        @Override
        public String run() {
            return (Math.random() < 0.5)?"Heads":"Tails";
        }
    }

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
     * Simulates a 6 sided dice roll
     *
     */
    class RollADie implements RandomApps {
        @Override
        public String getName() {
            return "Roll a 6-sided\ndie";
        }

        @Override
        public String run() {
            double random = Math.random() * 100;
            if (random < 16.6) {
                return "1";
            }
            else if (random < 33.2) {
                return "2";
            }
            else if (random < 49.8) {
                return "3";
            }
            else if (random < 66.4) {
                return "4";
            }
            else if (random < 83) {
                return "5";
            }
            else {
                return "6";
            }
        }
    }

    /**
     * Allows for arrays of RandomApps
     */
    public interface RandomApps {
        String getName();
        String run();
    }

}
import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * **********************
 * Description
 * **********************
 *
 * Simulates random chance events
 *
 * **********************
 * Analysis
 * **********************
 *
 * Inputs: Buttons
 * Outputs: LCD
 *
 * **********************
 * Pseudocode
 * **********************
 * Select Pressed
 *  Run Random method for option chosen
 *  Display result
 *
 */
public class Random implements LCDApps{
    private final RandomApps[] RANDOM_APPS = new RandomApps[] {new RollADie(),new FlipACoin()};
    private int currentMenu;

    /**
     * Gets menu ready
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

    /**
     * Gets name
     * @return name
     */
    @Override
    public String getName() {
        return "Random Selection";
    }

    /**
     * Handles button presses
     * @param ilcd LCD
     * @param button buttonpresses
     * @throws IOException
     */
    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
        menu(ilcd,button);
    }

    /**
     * Handles initial run
     * @param ilcd LCD
     * @throws IOException
     */
    @Override
    public void run(ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Use Right & Left\nto see options");
    }

    /**
     * **********************
     * Description
     * **********************
     *
     * Simulates a 2 sided coin being flipped
     *
     * **********************
     * Analysis
     * **********************
     *
     * Inputs: Random numbers
     * Outputs: Heads or Tails
     *
     * **********************
     * Pseudocode
     * **********************
     *
     * If Random Number from 0 - 1 is < 0.5 display Heads else display Tails
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
     * **********************
     * Description
     * **********************
     *
     * Simulates a 6 sided dice roll
     *
     * **********************
     * Analysis
     * **********************
     *
     * Inputs: Random number
     * Outputs: ints {1,2,3,4,5,6}
     *
     * **********************
     * Pseudocode
     * **********************
     *
     * Get a random number from 0 - 100
     * Return 1 - 6 based on what the random number is
     */
    class RollADie implements RandomApps {
        /**
         * Gets name
         * @return name
         */
        @Override
        public String getName() {
            return "Roll a 6-sided\ndie";
        }

        /**
         * Simulates a dice roll
         * @return 1 - 6
         */
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
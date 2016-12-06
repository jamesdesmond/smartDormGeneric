import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.Color;
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
 * Allows for the backlight of the LCD to be changed
 *
 */
public class Settings implements LCDApps {
    /**
     * Array of Color options
     */
    private static final Color[] COLORS = new Color[] {Color.RED,Color.GREEN,}; //On my hardware these were the only distinct colors
    /**
     * Stores current position in menu
     */
    private int currentMenu;

    /**
     * Default Constructor,
     * Gets the settings menu ready
     */
    public Settings() {
        currentMenu = -1;
    }

    @Override
    public String getName() {
        return "Change Backlight";
    }

    /**
     * Handles menu for Settings
     * @param ilcd LCD
     * @param button Handles button presses
     * @throws IOException
     */
    private void menu(ILCD ilcd, Button button)throws IOException {
        try {
            switch (button) {
                case RIGHT:
                    currentMenu++;
                    currentMenu = (currentMenu > COLORS.length - 1) ? 0 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(COLORS[currentMenu].toString());
                    break;
                case LEFT:
                    currentMenu--;
                    currentMenu = (currentMenu < 0) ? COLORS.length - 1 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(COLORS[currentMenu].toString());
                    break;
                case SELECT:
                    ilcd.setBacklight(COLORS[currentMenu]);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
        menu(ilcd,button);
    }

    @Override
    public void run(ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Use Right & Left\nto set backlight");
    }
}

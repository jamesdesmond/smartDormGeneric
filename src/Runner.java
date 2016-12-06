/**
 * Created by james on 9/24/16.
 */

import se.hirt.pi.adafruitlcd.*;
import se.hirt.pi.adafruitlcd.impl.RealLCD;

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
 * Runner manages the main menu of SmartDorm
 * as well as handling LCDApp opening and closing
 *
 * **********************
 * Analysis
 * **********************
 *
 * Creates a RealLCD in main()
 * Inputs: LCD buttons
 * Outputs: LCD
 *
 * **********************
 * Pseudocode
 * **********************
 * when downButton is pressed:
 *  show next item in LCDApps
 * when upButton is pressed:
 *  show previous item in LCDApps
 * when select is pressed:
 *  run currrent LCDApp
 *
 *
 */
public class Runner {
    /**
     * Default Constructor
     * Reads Configuration.ini,
     * sets inApp to false,
     * sets currentMenu to 0,
     * sets defaultColor to Red
     */
    public Runner() {
        ConfigurationEnums.readConfigurationFile();
        inApp = false;
        currentMenu = 0;
        defaultColor = Color.RED;
    }

    /**
     * Array of LCDApps, allowing easy addition and removal of programs from the main menu
     */
    private static final LCDApps[] APPS = new LCDApps[]{
            new showWeather(),
            new sendText(),
            new showIP(),
            new Random(),
            new Settings(),
            new Sleep()
    };
    /**
     * Is SmartDorm currently in an LCDApp
     */
    private boolean inApp;
    /**
     * Tracks index of the current menu item to be displayed
     */
    private int currentMenu;
    /**
     * Default color for the backlight
     */
    private Color defaultColor;

    /**
     * Handles button presses to show a main menu
     * @param ilcd LCD to be used
     * @throws IOException
     */
    private void menu(ILCD ilcd) throws IOException {
        ilcd.setBacklight(defaultColor);
        ilcd.clear();
        ilcd.setText(1 + ".)\n" + APPS[0].getName());
        ButtonPressedObserver observer = new ButtonPressedObserver(ilcd);
        observer.addButtonListener(new ButtonListener() {
            @Override
            public void onButtonPressed(Button button) {
                try {
                    if (!inApp) {
                        switch (button) {
                            case LEFT:
                                break;
                            case RIGHT:
                                break;
                            case DOWN:
                                currentMenu++;
                                if (currentMenu > APPS.length - 1) {
                                    currentMenu = 0;
                                }
                                ilcd.clear();
                                ilcd.setText(currentMenu + 1 + ".)\n" + APPS[currentMenu].getName());
                                break;
                            case UP:
                                currentMenu--;
                                if (currentMenu < 0) {
                                    currentMenu = APPS.length - 1;
                                }
                                ilcd.clear();
                                ilcd.setText(currentMenu + 1 + ".)\n" + APPS[currentMenu].getName());
                                break;
                            case SELECT:
                                APPS[currentMenu].run(ilcd);
                                inApp = true;
                                break;
                            default:
                                ilcd.clear();
                                ilcd.setText("Default case in Runner");
                                break;
                        }
                    } else {
                        if (button == Button.UP || button == Button.DOWN) {
                            inApp = false;
                            if (ilcd.getBacklight() == Color.OFF) {
                                ilcd.setBacklight(defaultColor);
                            }
                            ilcd.clear();
                            ilcd.setText(currentMenu + 1 + ".)\n" + APPS[currentMenu].getName());
                        } else {
                            APPS[currentMenu].run(ilcd, button);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        while (true) {
        }
    }

    /**
     * Creates the RealLCD and starts the menu
     * @param Args
     * @throws IOException
     */
    public static void main(String Args[]) throws IOException {
        final ILCD ilcd = new RealLCD();
        new Runner().menu(ilcd);
    }
}
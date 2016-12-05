/**
 * Created by james on 9/24/16.
 */

import se.hirt.pi.adafruitlcd.*;
import se.hirt.pi.adafruitlcd.impl.RealLCD;

import java.io.IOException;

/**
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
 *  @author james desmond
 */
public class Runner {
    public Runner() {
        ConfigurationEnums.readConfigurationFile();
        inApp = false;
        currentMenu = 0;
        defaultColor = Color.RED;
    }
    private static final LCDApps[] APPS = new LCDApps[]{
            new showWeather(),
            new sendText(),
            new showIP(),
            new Random(),
            new Settings(),
            new Sleep()
    };
    private boolean inApp;
    private int currentMenu;

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
/**
 * Created by james on 9/24/16.
 */

import se.hirt.pi.adafruitlcd.*;
import se.hirt.pi.adafruitlcd.mockup.MockupLCD;

import java.io.IOException;


public class Runner {
    public Runner() {
        ConfigurationEnums.readConfigurationFile();
        inApp = false;
        currentMenu = 0;
        defaultColor = Color.RED;
    }
    private static final LCDApps[] APPS = new LCDApps[]{
            //INSERT MENU ITEMS HERE
            //Be careful to only reference indices in peopleArrayList that have been filled
            new showWeather(),
            new sendText(), //TODO: Figure out how to make this work
            new showIP(),
            new Random(),
            new Settings(),
            new Sleep()
    };
    private boolean inApp;
    private int currentMenu;

    private Color defaultColor;

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

    public static void main(String Args[]) throws IOException {
        final ILCD ilcd = new MockupLCD();
        new Runner().menu(ilcd);
    }
}
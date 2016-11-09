import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.Color;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * Created by james on 10/26/16.
 */
public class Settings implements LCDApps {
    private static final Color[] COLORS = new Color[] {Color.RED,Color.GREEN,}; //On my hardware these were the only distinct colors
    private int currentMenu;
    public Settings() {
        currentMenu = -1;
    }
    @Override
    public String getName() {
        return "Change Backlight\nColor";
    }

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

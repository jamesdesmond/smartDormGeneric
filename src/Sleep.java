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
 * Turns off displays backlight
 *
 */
public class Sleep implements LCDApps {

    @Override
    public String getName() {
        return "Sleep Display";
    }

    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
    }

    @Override
    public void run(ILCD ilcd) throws IOException {
        ilcd.setBacklight(Color.OFF);
    }
}

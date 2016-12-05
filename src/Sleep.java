import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.Color;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * **********************
 * Description
 * **********************
 *
 * Turns off displays backlight
 *
 * **********************
 * Analysis
 * **********************
 *
 * Inputs: None
 * Outputs: LCD backlight
 *
 * **********************
 * Pseudocode
 * **********************
 *
 * Turn off backlight
 */
public class Sleep implements LCDApps {
    /**
     * Gets name
     * @return name
     */
    @Override
    public String getName() {
        return "Sleep Display";
    }

    /**
     * Does nothing
     * @param ilcd
     * @param button
     * @throws IOException
     */
    @Override
    public void run(ILCD ilcd, Button button) throws IOException {
    }

    /**
     * Turns the backlight off
     * @param ilcd
     * @throws IOException
     */
    @Override
    public void run(ILCD ilcd) throws IOException {
        ilcd.setBacklight(Color.OFF);
    }
}

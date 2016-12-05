import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * **********************
 * Description
 * **********************
 *
 * Shows the current IP of the machine
 *
 * **********************
 * Analysis
 * **********************
 * Inputs: none
 * Outputs: IP Address on LCD
 *
 * **********************
 * Pseudocode
 * **********************
 *
 * 1.Display IP address on screen
 */
public class showIP implements LCDApps {
    /**
     * Nothing to set up
     */
    public showIP(){};

    /**
     * Handles getting IP address from RPi
     * @return String IPaddress
     * @throws IOException
     */
    private  String getIP() throws IOException {
        return Util.getLocalAddress().toString().substring(1);

    }

    /**
     * Gets name
     * @return name
     */
    @Override
    public String getName() {
        return "Show IP Address";
    }

    /**
     * Handles button presses
     * @param ilcd LCD
     * @param button buttonpresses
     * @throws IOException
     */
    @Override
    public void run (ILCD ilcd, Button button) throws IOException {
        ilcd.clear();
        ilcd.setText("IP Address:\n" + getIP());
    }

    /**
     * Handles initial calling of ShowIP()
     * @param ilcd LCD
     * @throws IOException
     */
    public void run (ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("IP Address:\n" + getIP());
    }
}
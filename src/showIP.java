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
 * Shows the current IP of the machine
 *
 */

public class showIP implements LCDApps {
    /**
     * Default Constructor, nothing to do
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

    @Override
    public String getName() {
        return "Show IP Address";
    }

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
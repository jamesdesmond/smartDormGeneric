import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * @author James Desmond
 * SmartDormGeneric Final Assignment
 * CS1000-Fall 2016
 * Due: 12/6/16
 *
 * ******************
 * Description
 * **********************
 *
 * Interface for handling creation of new LCDApps
 *
 */

public interface LCDApps {
    String getName();
    void run(ILCD ilcd,Button button) throws IOException;
    void run(ILCD ilcd) throws IOException;
}
import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * **********************
 * Description
 * **********************
 *
 * Interface for handling creation of new LCDApps
 *
 * **********************
 * Analysis
 * **********************
 *
 * Inputs:none
 * Outputs:none
 *
 * **********************
 * Pseudocode
 * **********************
 *
 * n/a
 */

public interface LCDApps {
    String getName();
    void run(ILCD ilcd,Button button) throws IOException;
    void run(ILCD ilcd) throws IOException;
}
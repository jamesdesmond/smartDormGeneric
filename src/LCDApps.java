import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * Created by james on 10/13/16.
 */

public interface LCDApps {
    String getName();
    void run(ILCD ilcd,Button button) throws IOException;
    void run(ILCD ilcd) throws IOException;
}
package vislab.no.ntnu.barko.driver;


/**
 * Interface for a barko f22 projector.
 */
public interface F22Interface {
    int fetchBrightness();

    int setBrightness(int value);

    int fetchContrast();

    int setContrast(int value);

    int fetchLampRuntime(int lampNum);

    int getLampRemaining(int lampNum);

    int fetchTotalRuntime();

    int fetchLampStatus(int lampNum);

    int fetchTemperature();

    int testImageOn(int testImage);

    int testImageOff();
}

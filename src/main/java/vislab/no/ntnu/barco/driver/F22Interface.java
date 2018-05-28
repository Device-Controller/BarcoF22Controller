package vislab.no.ntnu.barco.driver;


/**
 * Interface for a barco f22 projector.
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

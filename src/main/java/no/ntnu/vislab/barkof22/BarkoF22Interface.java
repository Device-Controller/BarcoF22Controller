package no.ntnu.vislab.barkof22;


import no.ntnu.vislab.vislabcontroller.providers.Projector;

/**
 * Interface for a barko f22 projector.
 */
public interface BarkoF22Interface extends Projector {
    int getBrightness();

    int setBrightness(int value);

    int getContrast();

    int setContrast(int value);

    int getLampRuntime(int lampNum);

    int getLampRemaining(int lampNum);

    int getTotalRuntime();

    int getLampStatus(int lampNum);

    int getTemperature();

    int testImageOn(int testImage);

    int testImageOff();
}

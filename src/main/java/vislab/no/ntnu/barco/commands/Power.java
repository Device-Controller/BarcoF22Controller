package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 * @author ThomasSTodal
 */
public class Power extends F22Command {

    private static final String POWER = "POWR";
    public static final int ON = 1;
    public static final int OFF = 0;

    /**
     *
     */
    public Power(){
        super(POWER);
    }
    public Power(int setting) throws BarcoF22Exception {
        super(POWER, checkSetting(setting));
    }

    private static int checkSetting(int setting) throws BarcoF22Exception {
        if(setting != ON && setting != OFF){
            throw new BarcoF22Exception("Power setting must be 0 or 1");
        }
        return setting;
    }

    public int getPowerSetting() {
        return getValue();
    }
}

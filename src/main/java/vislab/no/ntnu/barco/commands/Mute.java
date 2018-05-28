package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 *
 * @author ThomasSTodal
 */
public class Mute extends F22Command {
    private static final String MUTE = "PMUT";
    private static final int MUTE_SETTING = 1;
    public static final int ON = 1;
    public static final int OFF = 0;

    /**
     *
     */
    public Mute(){
        super(MUTE);
    }

    /**
     *
      * @param setting
     * @throws BarcoF22Exception
     */
    public Mute(int setting) throws BarcoF22Exception {
        super(MUTE, checkSetting(setting));
    }

    /**
     *
     * @return
     */
    public int getMuteSetting(){
        return getValue();
    }

    /**
     *
     * @param setting
     * @return
     * @throws BarcoF22Exception
     */
    private static int checkSetting(int setting) throws BarcoF22Exception {
        if(setting != ON && setting != OFF){
            throw new BarcoF22Exception("Power setting must be 0 or 1");
        }
        return setting;
    }
}

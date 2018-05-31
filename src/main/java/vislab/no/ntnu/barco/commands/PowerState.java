package vislab.no.ntnu.barco.commands;

/**
 * @author ThomasSTodal
 */
public class PowerState extends F22Command {
    private static final String POWER_STATE = "POST";
    private static final int MAX_VALUE = 6;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    public PowerState() {
        super(POWER_STATE, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @return
     */
    public int getPowerState() {
        return getValue();
    }
}

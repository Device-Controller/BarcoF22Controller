package vislab.no.ntnu.barco.commands;

/**
 * @author ThomasSTodal
 */
public class ThermalStatus extends F22Command {
    private static final String THERMAL = "THRM";

    /**
     *
     */
    public ThermalStatus() {
        super(THERMAL);
    }

    /**
     *
     * @return
     */
    public int getThermal() {
        return getValue();
    }
}

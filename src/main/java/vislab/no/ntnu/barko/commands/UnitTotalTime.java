package vislab.no.ntnu.barko.commands;

/**
 * @author ThomasSTodal
 */
public class UnitTotalTime extends F22Command {
    private static final String TOTAL_TIME = "UTOT";

    /**
     *
     */
    public UnitTotalTime() {
        super(TOTAL_TIME);
    }

    /**
     *
     * @return
     */
    public int getTotalRuntime() {
        return getValue();
    }
}

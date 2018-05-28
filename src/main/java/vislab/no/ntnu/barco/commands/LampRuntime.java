package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 * @author ThomasSTodal
 */
public class LampRuntime extends F22Command {
    private static final String LAMP_RUNTIME = "LTR";
    private final int lampNum;

    /**
     *
     * @param integer
     */
    private LampRuntime(Integer integer) {
        super(LAMP_RUNTIME + integer);
        this.lampNum = integer;
    }

    /**
     *
     * @param lampNum
     * @throws BarcoF22Exception
     */
    public LampRuntime(int lampNum) throws BarcoF22Exception {
        this(new Integer(checkLampNum(lampNum)));
    }

    /**
     *
     * @param lampNum
     * @return
     * @throws BarcoF22Exception
     */
    private static int checkLampNum(int lampNum) throws BarcoF22Exception {
        if(lampNum >= 1 && lampNum <= 2) {
            return lampNum;
        } else {
            throw new BarcoF22Exception("Value is out of bounds!");
        }
    }

    /**
     *
     * @return
     */
    public int getLampNum() {
        return lampNum;
    }

    /**
     *
     * @return
     */
    public int getLampRuntime() {
        return getValue();
    }
}

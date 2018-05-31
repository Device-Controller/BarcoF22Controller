package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 * @author ThomasSTodal
 */
public class LampTimeRemaining extends F22Command {
    private static final String LAMP_TIME_REMAINING = "LRM";
    private final int lampNum;

    /**
     *
     * @param integer
     */
    private LampTimeRemaining(Integer integer) {
        super(LAMP_TIME_REMAINING + integer);
        this.lampNum = integer;
    }

    /**
     *
     * @param lampNum
     */
    public LampTimeRemaining(int lampNum) throws BarcoF22Exception {
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
    public int getLampTimeRemaining(){
        return getValue();
    }

    /**
     *
     * @return
     */
    public int getLampNum() {
        return lampNum;
    }
}

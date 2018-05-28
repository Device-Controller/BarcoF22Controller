package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 * @author ThomasSTodal
 */
public class Contrast extends F22Command {
    private static final String CONTRAST = "CNTR";
    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;
    private static final String RELATIVE_MODIFIER = "R";

    public Contrast() {
        super(CONTRAST, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @param integer
     * @param isAbsoluteValue
     */
    private Contrast(Integer integer, boolean isAbsoluteValue) {
        super((!isAbsoluteValue) ? CONTRAST + " " + RELATIVE_MODIFIER : CONTRAST + " ", integer);
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     * @throws BarcoF22Exception
     */
    public Contrast(int value, boolean isAbsoluteValue) throws BarcoF22Exception {
        this(new Integer(checkValue(value)), isAbsoluteValue);
    }

    /**
     *
     * @param value
     * @return
     * @throws BarcoF22Exception
     */
    private static int checkValue(int value) throws BarcoF22Exception {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            return value;
        } else {
            throw new BarcoF22Exception("Value is out of bounds!");
        }
    }

    /**
     *
     * @return
     */
    public int getContrast(){
        return getValue();
    }

    /**
     *
     * @param contrast
     * @throws BarcoF22Exception
     */
    public void setContrast(int contrast) throws BarcoF22Exception {
        setValue(checkValue(contrast));
    }
}

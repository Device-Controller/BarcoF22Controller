package vislab.no.ntnu.barco.commands;

import vislab.no.ntnu.barco.BarcoF22Exception;

/**
 * @author ThomasSTodal
 */
public class TestImage extends F22Command {
    private static final String TEST_IMAGE = "TEST";
    private static final int MAX_VALUE = 7;
    private static final int MIN_VALUE = 0;

    public TestImage(){
        super(TEST_IMAGE, 0);
    }
    /**
     *
     * @param integer
     */
    private TestImage(Integer integer) {
        super(TEST_IMAGE, integer, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @param patternNum
     * @throws BarcoF22Exception
     */
    public TestImage(int patternNum) throws BarcoF22Exception {
        this(new Integer(checkValue(patternNum)));
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
    public int getTestImage() {
        return getValue();
    }

    /**
     *
     * @param num
     * @throws BarcoF22Exception
     */
    public void setTestImage(int num) throws BarcoF22Exception {
        setValue(checkValue(num));
    }
}

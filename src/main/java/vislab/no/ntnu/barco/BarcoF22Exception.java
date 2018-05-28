package vislab.no.ntnu.barco;

/**
 * @author ThomasSTodal
 */
public class BarcoF22Exception extends Exception {
    private final String message;

    /**
     * @param message
     */
    public BarcoF22Exception(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }
}

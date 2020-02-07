package Firebase;

/**
 * Contain the information if it was a pull or push request
 */
public class Type {

    public String action;

    public Type() {
    }

    /**
     * @param action - String, If it is a Pull or Push request
     */
    public Type(String action) {
        this.action = action;

    }
}

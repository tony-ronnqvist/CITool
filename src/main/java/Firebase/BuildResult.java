package Firebase;

/**
 * Contain the information from the build result.
 * If it was successful or not.
 * What the message was
 * And what time this was done
 */
public class BuildResult {

    public boolean status;
    public String message;
    public String timestamp;

    public BuildResult() {

    }

    /**
     * @param status - Boolean, is true if the build was successful
     * @param message - String, information about the result
     * @param timestamp - String, The time the build was done
     */
    public BuildResult(boolean status, String message, String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}

package Firebase;
public class BuildResult {

    public boolean status;
    public String message;
    public String timestamp;

    public BuildResult() {

    }

    public BuildResult(boolean status, String message, String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;


    }
}

package Firebase;

/**
 * Contain the information from Pull and Push request
 */
public class PullRequest {


    public String url;
    public String issue_url;
    public int number;
    public String title;

    public PullRequest() {

    }

    /**
     * The request result
     * @param url - String
     * @param issue_url - String
     * @param number - String
     * @param title - String
     */
    public PullRequest(String url, String issue_url, int number, String title) {
        this.url = url;
        this.issue_url = issue_url;
        this.number = number;
        this.title = title;

    }

    /**
     * When only the url and issue_url exist
     * @param url - String
     * @param issue_url - String
     */
    public PullRequest(String url, String issue_url) {
        this.url = url;
        this.issue_url = issue_url;
    }

}

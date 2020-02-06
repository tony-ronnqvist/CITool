package Firebase;
public class PullRequest {


    public String url;
    public String issue_url;
    public int number;
    public String title;

    public PullRequest() {

    }

    public PullRequest(String url, String issue_url, int number, String title) {
        this.url = url;
        this.issue_url = issue_url;
        this.number = number;
        this.title = title;

    }

    public PullRequest(String url, String issue_url) {
        this.url = url;
        this.issue_url = issue_url;
    }

}

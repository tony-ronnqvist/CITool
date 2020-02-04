package Firebase;
public class PullRequest {


        public String url;
        public String node_id;
        public String issue_url;
        public int number;
        public String title;

        public PullRequest() {

        }

        public PullRequest(String url, String node_id, String issue_url, int number, String title) {
            this.url = url;
            this.node_id = node_id;
            this.issue_url = issue_url;
            this.number = number;
            this.title = title;

        }

}

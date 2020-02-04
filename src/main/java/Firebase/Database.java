package Firebase;
public class Database {

    public User user;
    public PullRequest pullrequest;
    public Body body;
    public BuildResult buildResult;

    public Database() {

    }

    public Database(PullRequest pullrequest, User user, Body body, BuildResult buildResult) {
        this.user = user;
        this.pullrequest = pullrequest;
        this.body = body;
        this.buildResult = buildResult;
    }
}

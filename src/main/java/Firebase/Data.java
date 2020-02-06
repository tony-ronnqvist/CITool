package Firebase;

public class Data {

    public PullRequest pullRequest;
    public User user;
    public BuildResult buildResult;

    public Data() {

    }

    public Data(PullRequest pullRequest, User user, BuildResult buildResult) {
        this.pullRequest = pullRequest;
        this.user = user;
        this.buildResult = buildResult;

    }


}

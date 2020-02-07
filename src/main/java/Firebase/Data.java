package Firebase;

/**
 * Contain the objects
 * Request, User, Result
 */
public class Data {

    public PullRequest pullRequest;
    public User user;
    public BuildResult buildResult;

    public Data() {

    }

    /**
     * @param pullRequest - PullRequest
     * @param user - User
     * @param buildResult - BuildResult
     */
    public Data(PullRequest pullRequest, User user, BuildResult buildResult) {
        this.pullRequest = pullRequest;
        this.user = user;
        this.buildResult = buildResult;

    }


}

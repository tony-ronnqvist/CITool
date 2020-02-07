package Firebase;

/**
 * Contain the information about the user doing the pull or push request
 */
public class User {

    public String login;
    public String avatar_url;

    public User() {

    }

    /**
     * @param login - String, the users login
     * @param avatar_url - String, the users avatar picture
     */
    public User(String login, String avatar_url) {
        this.login = login;
        this.avatar_url = avatar_url;


    }
}

import Firebase.Data;
import Firebase.BuildResult;
import Firebase.Database;
import Firebase.PullRequest;
import Firebase.User;
import Firebase.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.api.client.json.JsonString;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


/**
 ContinuousIntegrationServer for windows using github integration
 */
public class CIServer extends AbstractHandler {

    Firestore dbAdmin;
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Creates a connection with the database
     * @throws IOException
     */
    public CIServer() throws IOException {
        System.out.println("First time to get connected to database");
        // Commutations between server and firebase
        FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://citool.firebaseio.com")
                .build();

        FirebaseApp myDatabase = FirebaseApp.initializeApp(options, "Admin");
        dbAdmin = FirestoreClient.getFirestore(myDatabase);

    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        //Extract the event type (push or pull-request)
        String headerValue = JsonParser.getGitHubEventFromHeader(request);

        //Lock and wait until request has been handled
        lock.lock();
        try {

            //If headerValue is pull_request run scripts and log data
            if (headerValue.equals("push")) {
                String eventType = "push";
                //Print that new puss was received by server
                System.out.println("Received new push request");

                //Get the payload and represent the json as string jsonString
                String jsonString = JsonParser.getJsonFromRequest(request);
                //Send response to github that project is pending
                status_API(jsonString, "pending", eventType);

                //Run script for push
                String [] responseScriptPush = ServerControl.cloneAndBuildWin(jsonString, "PUSH");
                createClassesPush(jsonString, responseScriptPush);

                //Send response to github that project failed or succeeded
                if (responseScriptPush.equals("0")) {
                    status_API(jsonString, "success",eventType);
                } else {
                    status_API(jsonString, "failure",eventType);
                }

            }

            //If headerValue is pull_request run scripts and log data
            if (headerValue.equals("pull_request")) {
                String eventType = "pull_request";
                //Print that new puss was received by server
                System.out.println("Received new pull request");

                //Get the payload and represent the json as string jsonString
                String jsonString = JsonParser.getJsonFromRequest(request);
                System.out.println(jsonString);
                System.out.println(JsonParser.get_number(jsonString));

                //Send response to github that project is pending
                status_API(jsonString, "pending", eventType );

                //Run script for pull request
                String[] responseScriptPull = ServerControl.cloneAndBuildWin(jsonString,"PULL");
                createClassesPull(jsonString, responseScriptPull);

                //Send response to github that project failed or succeeded
                if (responseScriptPull.equals("0")) {
                    status_API(jsonString, "success", eventType );
                } else {
                    status_API(jsonString, "failure", eventType );
                }

            }
        } finally {
            //When process has finished running unlock and let next request through
            lock.unlock();
        }
    }

    /**
     * Creates the push classes: PullRequest, User, BuildResult, Data, Type
     *
     * @param request
     */
    public void createClassesPush(String jsonString, String[] responseScript){
        String action = "PUSH";

        PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url_push(jsonString),JsonParser.get_url_push(jsonString));

        User user = new User(JsonParser.get_name_push(jsonString), JsonParser.get_avatar_url_push(jsonString));

        Data data = new Data(pullrequest, user, buildMessages(responseScript));

        Type type = new Type(action);

        updateDatabase(type, data, ("Pu" +JsonParser.get_sha_push(jsonString)));

    }

    /**
     * Getting the Description base on state
     *
     * @param state String - api status after testing
     * @return description string - description after checking
     */
    public static String getDescription(String state) {
        if (state.equals("success")) return "Build completed";
        if (state.equals("error")) return "Build error";
        if (state.equals("failure")) return "Build has failed";
        if (state.equals("pending")) return "still building...";
        return "error state";
    }

    /**
     * Sending status api back to github
     * A string return for the connection condition is generated.
     *
     * @param jsonString String - Pull request information
     * @param state      String - api status after testing
     * @return string - Connection condition for checking
     */
    public static String status_API(String jsonString, String state, String eventType) throws UnsupportedEncodingException {
        String owner = "", repo = "", sha = "", description = "",id="";
        if (eventType.equals("push")) {
            owner = JsonParser.get_login_push(jsonString);
            repo = JsonParser.get_name_push(jsonString);
            sha = JsonParser.get_sha_push(jsonString);
            id = "Pu" +JsonParser.get_sha_push(jsonString)
        } else if (eventType.equals("pull_request")) {
            owner = JsonParser.get_login(jsonString);
            repo = JsonParser.get_full_name(jsonString);
            sha = JsonParser.get_sha_pull_request(jsonString);
            id = "Pl" +JsonParser.get_sha_pull_request(jsonString)
        }

        description = getDescription(state);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();


        if (description.equals("error state")) {
            return "Unacceptable state";
        }

        try {
            StringEntity body = new StringEntity("{\"state\": \"" + state + "\", " +
                    "\"target_url\": \"https://citools.firebaseapp.com/builds/" +id + "\"," +
                    "\"description\": \"" + description + "\" ," +
                    "\"context\": \"continuous-integration\" }");
            String url = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;
            HttpPost post = new HttpPost(url);
            post.setEntity(body);
            post.addHeader("Authorization", "token " + Token.getToken());
            HttpResponse response = httpClient.execute(post);
            System.out.println("RESPONSECODE: " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    return "good";
                } catch (Exception e) {
                    return "entity error";
                }
            }
            //System.out.println("url: " + url );
        } catch (Exception ex) {
            return "error " + ex;
        }
        return "not tried";
    }

    /**
     * Creates the push classes: PullRequest, User, BuildResult, Data, Type
     * @param request
     */
    public void createClassesPull(String jsonString, String[] responseScript){
        String action = "PULLREQUEST";

        BigInteger number = new BigInteger(JsonParser.get_number(jsonString));
        PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url(jsonString),JsonParser.get_issue_url(jsonString), number.intValue(),JsonParser.get_title(jsonString));

        User user = new User(JsonParser.get_full_name(jsonString), JsonParser.get_avatar_url(jsonString));

        Data data = new Data(pullrequest, user, buildMessages(responseScript));

        Type type = new Type(action);

        updateDatabase(type, data, ("Pl"+JsonParser.get_sha_pull_request(jsonString)));

    }

    /**
     * Updates the database on firebase with either pull och push information
     * @param type
     * @param data
     * @param jsonString
     */
    public void updateDatabase(Type type, Data data, String jsonString){

        Database database = new Database(type, data);

        String childPath = "";
        if (jsonString.equals("Plnull")){
            int x = (int)(Math.random()*((1000000000-100)+1))+100;
            String numberPull = Integer.toString(x);
            childPath = "Pl" + numberPull;
        }else {
            childPath = jsonString;
        }

        dbAdmin.collection("builds").document(childPath).set(database);
    }

    /**
     * Takes a script response and change it to a BuildResult
     *
     * @param responseScript String array - with exit code and exit messages
     * @return BuildResult - correctly formatted
     */
    public static BuildResult buildMessages(String[] responseScript){
        //Init. exit code as boolean
        boolean exitCode = false;

        String exitMessages = responseScript[1];

        if (responseScript[0].equals("0")) {
            exitCode = true;
            exitMessages = "The build was successful";
        }

        //Fix date and format
        SimpleDateFormat buildDateFormat = new SimpleDateFormat("yyyy-MM-dd '@' HH:mm:ss");
        Date buildDate = new Date(System.currentTimeMillis());

        //create a BuildResult

        return new BuildResult(exitCode, exitMessages, buildDateFormat.format(buildDate));
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new CIServer());
        server.start();
        server.join();
    }
}


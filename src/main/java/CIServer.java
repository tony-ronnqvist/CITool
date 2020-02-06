import Firebase.Data;
import Firebase.BuildResult;
import Firebase.Database;
import Firebase.PullRequest;
import Firebase.User;
import Firebase.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import com.google.api.client.json.JsonString;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


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
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class CIServer extends AbstractHandler {

    Firestore dbAdmin;

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
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        //Extract the event type (push or pull-request)
        String headerValue = JsonParser.getGitHubEventFromHeader(request);


        // Server listens for push or pull
        if(headerValue.equals("push")){
            createClassesPush(request);
        }
        if(headerValue.equals("pull_request")){
            createClassesPull(request);
        }

    }

    /**
     * Creates the push classes: PullRequest, User, BuildResult, Data, Type
     * @param request
     */
    public void createClassesPush(HttpServletRequest request){
        String action = "PUSH";

        String jsonString = JsonParser.getJsonFromRequest(request);

        PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url_push(jsonString),JsonParser.get_url_push(jsonString));

        User user = new User(JsonParser.get_name_push(jsonString), JsonParser.get_avatar_url_push(jsonString));

        BuildResult buildResult = new BuildResult(true, "This build is unsuccessful", "2020-02-06T13:00:04.293Z");  // --> väntar på update

        Data data = new Data(pullrequest, user, buildResult);

        Type type = new Type(action);

        updateDatabase(type, data, ("Pu" +JsonParser.get_sha_push(jsonString)));

    }

    /**
     * Creates the push classes: PullRequest, User, BuildResult, Data, Type
     * @param request
     */
    public void createClassesPull(HttpServletRequest request){
        String action = "PULLREQUEST";

        String jsonString = JsonParser.getJsonFromRequest(request);

        BigInteger number = new BigInteger(JsonParser.get_number(jsonString));
        PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url(jsonString),JsonParser.get_issue_url(jsonString), number.intValue(),JsonParser.get_title(jsonString));

        User user = new User(JsonParser.get_full_name(jsonString), JsonParser.get_avatar_url(jsonString));

        BuildResult buildResult = new BuildResult(false, "This build is unsuccessful", "2020-02-06T13:00:04.293Z");

        Data data = new Data(pullrequest, user, buildResult);

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

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new CIServer());
        server.start();
        server.join();
    }
}
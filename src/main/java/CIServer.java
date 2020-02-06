import Firebase.Body;
import Firebase.BuildResult;
import Firebase.Database;
import Firebase.PullRequest;
import Firebase.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

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
public class CIServer extends AbstractHandler
{

    Firestore dbAdmin;

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
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        //Extract the event type (push or pull-request)
        String headerValue =  JsonParser.getGitHubEventFromHeader(request);


        if(headerValue.equals("push")){

        }
        if(headerValue.equals("pull_request")){
            String jsonString = JsonParser.getJsonFromRequest(request);

            BigInteger number = new BigInteger(JsonParser.get_number(jsonString));
            PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url(jsonString),JsonParser.get_issue_url(jsonString), number.intValue(),JsonParser.get_title(jsonString));

            User user = new User(JsonParser.get_full_name(jsonString), JsonParser.get_avatar_url(jsonString));

            Body body = new Body(JsonParser.get_updated_at(jsonString));

            BuildResult buildResult = new BuildResult(false, "This build is unsuccessful");


            Database database = new Database(pullrequest, user, body, buildResult);

            // updateDatabase(db, database);


            //This is the ID of the Pull_request.
            String childPath = ("3511124144");

            //Sending a new uppdate --
           // dbAdmin.collection("builds").document(childPath).set(database);

        }

    }

//    public void updateDatabase(HttpServletResponse response) throws IOException {
//
//
//    }



    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new CIServer());
        server.start();
        server.join();
    }
}
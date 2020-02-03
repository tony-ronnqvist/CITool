import Firebase.Body;
import Firebase.BuildResult;
import Firebase.Database;
import Firebase.PullRequest;
import Firebase.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

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
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        //Updating the database with new information
        updateDatabase(response);

      //  response.getWriter().println("CI job done");
    }

    public void updateDatabase(HttpServletResponse response) throws IOException {

        // Commutations between server and firebase
        FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://citool.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();


        //One example that needs to add to the classes.
        PullRequest pullrequest = new PullRequest("https://github.com/repos/feluxz/CITEST/pulls/13","MDExOlB1bGxSZXF1ZXN0MzY5OTA1MDgy", "https://github.com/repos/feluxz/CITEST/issues/13", 13, "soijsfoij");

        User user = new User("feluxz", "https://avatars0.githubusercontent.com/u/29494534?v=4");

        Body body = new Body("2020-02-03T14:26:58Z");

        BuildResult buildResult = new BuildResult(true, "Lorem ipsum");
        //Done with example


        //This is what is sent to Firebase with set()
        Database database = new Database(pullrequest, user, body, buildResult);

        //This is the ID of the Pull_request.
        String childPath = "369905082";

        //Sending a new uppdate
        ApiFuture<WriteResult> future = db.collection("builds").document(childPath).set(database);
        try {
            response.getWriter().println("Done: " + future.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

/*        // getting the update
        DocumentReference docref = db.collection("builds").document("9e52161");
        ApiFuture<DocumentSnapshot> future = docref.get();
        try {
            DocumentSnapshot document = future.get();

            response.getWriter().println(document.getData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        //response.getWriter().println(future.get().getUpdateTime());
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
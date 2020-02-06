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
import java.util.Map;

import com.google.api.client.json.JsonString;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


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
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class CIServer extends AbstractHandler {

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
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        //Extract the event type (push or pull-request)
        String headerValue = JsonParser.getGitHubEventFromHeader(request);


        if (headerValue.equals("push")) {
            String action = "PUSH";

        }


        if (headerValue.equals("pull_request")) {


            String action = "PULLREQUEST";

            //Get the payload and represent the json as string jsonString
            String[] responseScript;
            String jsonString = JsonParser.getJsonFromRequest(request);
            responseScript = ServerControl.cloneAndBuildWin(jsonString);
            System.out.printf("%s - %s", responseScript[0], responseScript[1]);


            BigInteger number = new BigInteger(JsonParser.get_number(jsonString));
            PullRequest pullrequest = new PullRequest(JsonParser.get_clone_url(jsonString), JsonParser.get_issue_url(jsonString), number.intValue(), JsonParser.get_title(jsonString));

            User user = new User(JsonParser.get_full_name(jsonString), JsonParser.get_avatar_url(jsonString));

            BuildResult buildResult = new BuildResult(false, "This build is unsuccessful", "2020-02-06T13:00:04.293Z");

            Data data = new Data(pullrequest, user, buildResult);

            //-------------

            Type type = new Type(action);

            Database database = new Database(type, data);

            // updateDatabase(db, database);


            //This is the ID of the Pull_request.
            String childPath = ("df7f75ea3b0e2686a41759dd00cc6289feda4c15");
        }
        
    }

    /**
     *Getting the Description base on state
     *@param state String - api status after testing
     *@return description string - description after checking
     */
    public static String getDescription(String state){
        if (state.equals("success")) return "Build completed";
        if (state.equals("error")) return "Build error";
        if (state.equals("failure")) return "Build has failed";
        if (state.equals("pending")) return "still building...";
        return "error state";
    }

    /**
     *Sending status api back to github
     *A string return for the connection condition is generated.
     *@param jsonString String - Pull request information
     *@param state String - api status after testing
     *@return string - Connection condition for checking
     */
    public static String status_API(String jsonString, String state) throws UnsupportedEncodingException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String owner,repo,sha,description;
        owner=JsonParser.get_login(jsonString);
        repo=JsonParser.get_full_name(jsonString);
        sha=JsonParser.get_sha_pull_request(jsonString);
        description=getDescription(state);
        if (description.equals("error state")) {
            return "Unacceptable state";
        }

        try {
            StringEntity body = new StringEntity("{\"state\": \"" +state +"\", " +
                    "\"target_url\": \"https://citools.firebaseapp.com/builds/3\"," +
                    "\"description\": \"" + description +"\" ," +
                    "\"context\": \"continuous-integration\" }");
            String url = "https://api.github.com/repos/"+owner+ "/"+repo+"/statuses/"+sha;
            HttpPost post = new HttpPost(url);
            post.setEntity(body);
            post.addHeader("Authorization", "token " + Token.getToken());
            HttpResponse response = httpClient.execute(post);
            System.out.println("RESPONSECODE: " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    return "good";
                }catch (Exception e){
                    return "entity error";
                }
            }
            //System.out.println("url: " + url );
        }catch (Exception ex) {
            return "error "+ ex;
        }
        return "not tried";
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


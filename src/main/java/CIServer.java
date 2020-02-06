import Firebase.Body;
import Firebase.BuildResult;
import Firebase.Database;
import Firebase.PullRequest;
import Firebase.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.*;
import java.util.Map;

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

        //Get the payload and represent the json as string jsonString
        String jsonString = JsonParser.getJsonFromRequest(request);

        //Extract the event type (push or pull-request)
        String headerValue =  JsonParser.getGitHubEventFromHeader(request);

        //Convert string to map and extract the key
        Gson gson = new Gson();
        Map map = gson.fromJson(jsonString, Map.class);

        if(headerValue.equals("push")){

        }
        if(headerValue.equals("pull_request")){
            System.out.println(map.get("number"));
            status_API(jsonString,"success");
        }




        //Updating the database with new information

        //updateDatabase(response);

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

    /**
     *A Description return based on state.
     *@param state String - api status after testing
     *@return description string - Description for state
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
        return "";
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


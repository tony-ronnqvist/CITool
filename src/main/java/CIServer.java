import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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
            System.out.println(map.get("ref"));
            System.out.println("COMMIT: " + JsonParser.getCommitId(jsonString));
        }
        if(headerValue.equals("pull_request")){
            System.out.println(map.get("number"));
        }


        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
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
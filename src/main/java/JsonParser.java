import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;

public final class JsonParser {

    public static String getCommitId(String jsonString){
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        //Extract the commit json array
        JsonArray properties = jsonStringAsObject.getAsJsonArray("commits");
        //Get the first object from the array
        JsonObject propertiesJson = properties.get(0).getAsJsonObject();
        //return id
        return propertiesJson.get("id").toString();
    }
    public static String getJsonFromRequest(HttpServletRequest request){
        //Get the payload and represent the json as string jsonString
        StringBuilder jsonBuff = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jsonBuff.append(line);
        } catch (Exception e) { /*error*/ }
        //return whole json as a string.
        return jsonBuff.toString();
    }
    public static String getGitHubEventFromHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String headerValue = "";
        //Go through all the header names and extract the X-GitHub-Event
        //(push or pull request)
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if(headerName.equals("X-GitHub-Event")){
                headerValue = request.getHeader(headerName);
            }

        }
        //Return push of pull-request as a string
        return headerValue;
    }
}
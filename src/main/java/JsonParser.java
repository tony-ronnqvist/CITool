import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Map;

public final class JsonParser {
    /**
     * Gets the sha from a "pull-request" json string
     * @param jsonString
     * @return String - containing full_name from github payload
     */
    public static String get_sha_pull_request(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();

        //Create map of user object
        Map map = gson.fromJson(jsonStringAsObject, Map.class);
        //Return the full_name value
        return (map.get("after") == null) ? "null" : map.get("after").toString();
    }

     /**
     * Gets the clone_url from a "pull-request" json string
     * @param jsonString
     * @return String - containing clone_url from github payload
     */
    public static String get_clone_url(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Extract the head object
        JsonObject user = pull_request.getAsJsonObject("head");
        //Extract the repo object
        JsonObject repo = user.getAsJsonObject("repo");
        //Create map of user object
        Map map = gson.fromJson(repo, Map.class);
        //Return the clone_url value
        return (map.get("clone_url") == null) ? "null" : map.get("clone_url").toString();
    }

    /**
     * Gets the full_name from a "pull-request" json string
     * @param jsonString
     * @return String - containing full_name from github payload
     */
    public static String get_full_name(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Extract the head object
        JsonObject user = pull_request.getAsJsonObject("head");
        //Extract the repo object
        JsonObject repo = user.getAsJsonObject("repo");
        //Create map of user object
        Map map = gson.fromJson(repo, Map.class);
        //Return the full_name value
        return (map.get("full_name") == null) ? "null" : map.get("full_name").toString();
    }
    /**
     * Gets the title from a "pull-request" json string
     * @param jsonString
     * @return String - containing title from github payload
     */
    public static String get_title(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the title value
        return (map.get("title") == null) ? "null" :  map.get("title").toString();
    }
    /**
     * Gets the number from a "pull-request" json string
     * @param jsonString
     * @return String - containing number from github payload
     */
    public static String get_number(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Remove decimals from the number
        String number = String.format("%.0f", map.get("number"));

        return number;
    }
    /**
     * Gets the issue_url from a "pull-request" json string
     * @param jsonString
     * @return String - containing issue_url from github payload
     */
    public static String get_issue_url(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the issue_at value
        return (map.get("issue_url") == null) ? "null" : map.get("issue_url").toString();
    }
    /**
     * Gets the node_id from a "pull-request" json string
     * @param jsonString
     * @return String - containing node_id from github payload
     */
    public static String get_node_id(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the node_id value
        return (map.get("node_id") == null) ? "null" : map.get("node_id").toString();
    }
    /**
     * Gets the get_url from a "pull-request" json string
     * @param jsonString
     * @return String - containing get_url from github payload
     */
    public static String get_url(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the url value
        return (map.get("url") == null) ? "null" : map.get("url").toString();
    }
    /**
     * Gets the avatar_url from a "pull-request" json string
     * @param jsonString
     * @return String - containing avatar_url from github payload
     */
    public static String get_avatar_url(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Extract the user object
        JsonObject user = pull_request.getAsJsonObject("user");
        //Create map of user object.
        Map map = gson.fromJson(user, Map.class);
        //Return the avatar_url value
        return (map.get("avatar_url") == null) ? "null" : map.get("avatar_url").toString();
    }
    /**
     * Gets the login from a "pull-request" json string
     * @param jsonString
     * @return String - containing login from github payload
     */
    public static String get_login(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Extract the user object
        JsonObject user = pull_request.getAsJsonObject("user");
        //Create map of user object.
        Map map = gson.fromJson(user, Map.class);
        //Return the login value
        return (map.get("login") == null) ? "null" : map.get("login").toString();
    }
    /**
     * Gets the updated_at from a "pull-request" json string
     * @param jsonString
     * @return String - containing updated_at from github payload
     */
    public static String get_updated_at(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the updated_at value
        return (map.get("updated_at") == null) ? "null" : map.get("updated_at").toString();
    }
    /**
     * Gets the merged_at from a "pull-request" json string
     * @param jsonString
     * @return String - containing merged_at from github payload
     */
    public static String get_merged_at(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the merged_at value or null as string
        return (map.get("merged_at") == null) ? "null" : map.get("merged_at").toString();
    }
    /**
     * Gets the closed_at from a "pull-request" json string
     * @param jsonString
     * @return String - containing closed_at from github payload
     */
    public static String get_closed_at(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Create map of pull-request object.
        Map map = gson.fromJson(pull_request, Map.class);
        //Return the closed_at value
        return (map.get("closed_at") == null) ? "null" : map.get("closed_at").toString();
    }
    /**
     * Gets the id from a "commits" array json string
     * @param jsonString
     * @return String - containing id from github payload
     */
    public static String get_commitId(String jsonString){
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        //Extract the commit json array
        JsonArray pull_request = jsonStringAsObject.getAsJsonArray("commits");
        //Get the first object from the array
        JsonObject pull_requestJson = pull_request.get(0).getAsJsonObject();
        //return id
        return pull_requestJson.get("id").toString();
    }
    /**
     * Gets the json String the HttpServletRequest
     * @param HttpServletRequest - request
     * @return String - containing json from github payload
     */
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

    /**
     * Gets event from the header in the HttpServletRequest
     * @param HttpServletRequest - request
     * @return String - containing event type from github header
     */
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
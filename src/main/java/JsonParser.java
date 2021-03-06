import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.Map;

public final class JsonParser {
    /**
     * PUSH
     */
    /**
     * Gets the commit message from a "push" json string
     * @param jsonString - String
     * @return String - containing message from github payload
     */
    public static String get_message_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the commit json array
        JsonArray pull_request = jsonStringAsObject.getAsJsonArray("commits");
        //Get the first object from the array
        JsonObject pull_requestJson = pull_request.get(0).getAsJsonObject();
        //Return the name value
        return (pull_requestJson.get("message") == null) ? "null" : pull_requestJson.get("message").toString().replace("\"", "");
    }


    /**
     * Gets the clone_url from a "push" json string
     * @param jsonString - String
     * @return String - containing clone_url from github payload
     */
    public static String get_clone_url_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Create map of repository object
        Map map = gson.fromJson(repository, Map.class);
        //Return the name value
        return (map.get("clone_url") == null) ? "null" : map.get("clone_url").toString();
    }

    /**
     * Gets the sha from a "push" json string
     * @param jsonString - String
     * @return String - containing full_name from github payload
     */
    public static String get_sha_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();

        //Create map of user object
        Map map = gson.fromJson(jsonStringAsObject, Map.class);
        //Return the after value
        return (map.get("after") == null) ? "null" : map.get("after").toString();
    }

    /**
     * Gets the name from a "push" json string
     * @param jsonString - String
     * @return String - containing name from github payload
     */
    public static String get_name_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Create map of user object
        Map map = gson.fromJson(repository, Map.class);
        //Return the name value
        return (map.get("name") == null) ? "null" : map.get("name").toString();
    }


    /**
     * Gets the url from a "push" json string
     * @param jsonString - String
     * @return String - containing url from github payload
     */
    public static String get_url_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the repository object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Create map of repo object
        Map map = gson.fromJson(repository, Map.class);
        //Return the url value
        return (map.get("url") == null) ? "null" : map.get("url").toString();
    }
    /**
     * Gets the url from a "push" json string
     * @param jsonString - String
     * @return String - containing url from github payload
     */
    public static String get_updated_at_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Create map of user object
        Map map = gson.fromJson(repository, Map.class);
        //Return the url value
        return (map.get("updated_at") == null) ? "null" : map.get("updated_at").toString();
    }
    /**
     * Gets the login from a "push" json string
     * @param jsonString - String
     * @return String - containing login from github payload
     */
    public static String get_login_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the repository object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Extract the owner object
        JsonObject owner = repository.getAsJsonObject("owner");
        //Create map of  owner object
        Map map = gson.fromJson(owner, Map.class);
        //Return the login value
        return (map.get("login") == null) ? "null" : map.get("login").toString();
    }
    /**
     * Gets the login from a "push" json string
     * @param jsonString - String
     * @return String - containing login from github payload
     */
    public static String get_avatar_url_push(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the repository object
        JsonObject repository = jsonStringAsObject.getAsJsonObject("repository");
        //Extract the owner object
        JsonObject owner = repository.getAsJsonObject("owner");
        //Create map of user object
        Map map = gson.fromJson(owner, Map.class);
        //Return the url value
        return (map.get("avatar_url") == null) ? "null" : map.get("avatar_url").toString();
    }


    /**
    * PULL-REQUESTS
    */


    /**
     * Gets the sha from a "pull-request" json string
     * @param jsonString - String
     * @return String - containing full_name from github payload
     */
    public static String get_sha_pull_request(String jsonString){
        //Create jsonObject of jsonString
        JsonObject jsonStringAsObject = new Gson().fromJson(jsonString, JsonObject.class);
        Gson gson = new Gson();
        //Extract the pull-request object
        JsonObject pull_request = jsonStringAsObject.getAsJsonObject("pull_request");
        //Extract the head object
        JsonObject user = pull_request.getAsJsonObject("head");
        //Create map of user object
        Map map = gson.fromJson(user, Map.class);
        //Return the full_name value
        return (map.get("sha") == null) ? "null" : map.get("sha").toString();
    }

     /**
     * Gets the clone_url from a "pull-request" json string
     * @param jsonString - String
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
     * Gets the name from a "pull-request" json string
     * @param jsonString - String
     * @return String - containing name from github payload
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
        return (map.get("name") == null) ? "null" : map.get("name").toString();
    }
    /**
     * Gets the title from a "pull-request" json string
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param jsonString - String
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
     * @param request - HttpServletRequest
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
     * @param request - HttpServletRequest
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
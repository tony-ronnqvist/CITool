import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

/**
 Getting the github access token
 Change is needed after pull
*/

public class Token {
    private static String token;
    public static String getToken()throws Exception{
        String a=System.getProperty("java.class.path");
        String[] path=a.split("build");
        System.out.println(path[0]);
        File f = new File(path[0] +"token.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        token=reader.readLine();
        return token;
    }

}

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for controlling logging information and controlling shell on the continuous integration server
 */
public class ServerControl {

    /**
     * Runs a specified command on a given shell in a given working directory.
     * E.g. "Cmd.exe", /c, cd, test, with working directory "C:/users" would change
     * directory to test from the directory C:/user if such a directory exist. If such a
     * directory exist the method returns an array with exit code 0 and no error messages,
     * else it returns a array with an exit code not zero and an error messages.
     *
     * @param directory     string - specifying desired working directory
     * @param commandString strings - specifying the commands to be executed in order
     * @return String array - with the commands exit code and error messages if any
     * @throws IOException - if an I/O error occurs
     */
    public static String[] runCommand(String directory, String... commandString)
            throws IOException {

        //Initialize output array
        String[] output = new String[2];

        //Create new process builder
        ProcessBuilder build = new ProcessBuilder();

        //Set working directory where the command should run
        build.directory(new File(directory));

        //Set environment and command string
        build.command(commandString);

        //Start process, i.e. run command
        Process process = build.start();

        //Get exit code from process, 0 = successful else not, catch if interrupted
        try {
            //Get exit code from process, 0 = successful else not
            int errorValueInt = process.waitFor();

            //Convert exit code to string and insert in output
            output[0] = String.valueOf(errorValueInt);

            //Get error messages for error as string and insert in output
            output[1] = convertStreamToString(process.getErrorStream());
            //Catch InterruptedException
        } catch (InterruptedException e) {
            e.getStackTrace();
            output[0] = "500";
            output[1] = "Interrupted";
        }

        return output;
    }

    /**
     * Converts a InputStream of bytes to a string
     *
     * @param inputStream InputStream - with bytes to read
     * @return String - of bytes in inputStream
     * @throws IOException -
     */
    private static String convertStreamToString(InputStream inputStream) throws IOException {

        //Initialize lengthCheck
        int lengthCheck;

        //Generate buffer for writing to bytes array.
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        //While .read() not -1, continue reading still something left in stream
        while ((lengthCheck = inputStream.read(buffer)) != -1) {
            //Write to bytes
            bytes.write(buffer, 0, lengthCheck);
        }

        //Convert all bytes to string representation
        String bytesAsString = bytes.toString("UTF-8");

        return bytesAsString;
    }

    /**
     * Generates a log file in src/main with name ServerLog.txt if non exists and appends the log data to the file.
     * If file exist appends the log data to the file.
     *
     * @param logData String array - first entry exit code and second entry error messages if any
     * @throws IOException -  if logfile exists but is a directory rather than a regular file,
     *                     does not exist but cannot be created, or cannot be opened for any other reason
     */
    public static void logDataToFile(String[] logData) throws IOException {

        //Generate date and format it
        SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd '@' HH:mm:ss");
        Date logDate = new Date(System.currentTimeMillis());

        String logEntry;

        //Generate log entry as string from data
        if (logData[0].equals("0")) {

            //If exit code is 0 then it was successful
            logEntry = logDateFormat.format(logDate) + " - Exit Code: " + logData[0] +
                    " - Successful\n";

        } else {

            //if not append the error messages instead
            logEntry = logDateFormat.format(logDate) + " - Exit Code: " + logData[0] +
                    " - Failed - Error: " + logData[1];
        }

        //Set log file name
        String logFilename = "ServerLog.txt";

        //Make log to a FileWriter and set append to true
        FileWriter newLog = new FileWriter(logFilename, true);

        //Make the log file then append entry to logfile and close file
        newLog.write(logEntry);
        newLog.close();

    }

    /**
     * Gives the shell and run & close command for the current operating system. Only checking for
     * operating systems Linux, Windows and Mac; if not mac or windows assumes that it is Linux.
     *
     * @return String array - with the shell for the current os and the run and close setting
     */
    public static String[] getOsShell() {
        String[] output = new String[3];

        output[2] = System.getProperty("os.name");

        //Check current operating system is Windows
        if (output[2].startsWith("Windows")) {
            output[0] = "Cmd.exe";
            output[1] = "/c";

        } else if (output[2].startsWith("Mac")) {
            //else check if current operating system is Mac (Don't know if this works)
            output[0] = "zsh";
            output[1] = "-c";

        } else {
            //Else assume operating system is Linux
            output[0] = "bash";
            output[1] = "-c";
        }
        return output;
    }

    public static String[] cloneAndBuild(String json) throws IOException {

        //Init shell for os
        String[] osShell;

        //Get all required strings from JsonParser.
        String gitAddress = JsonParser.get_clone_url(json);
        String gitId = JsonParser.get_sha_pull_request(json);
        String gitDirectory = JsonParser.get_full_name(json);

        //Get the shell for current os
        osShell = getOsShell();

        //Sets which directory to clone to
        String cloneDirectory = System.getProperty("user.dir");

        String[] result1; String[] result2; String[] result3; String[] result4;

        //Run git clone on git address and log output
        result1 = runCommand(cloneDirectory, osShell[0], osShell[1], "git", "clone", gitAddress);
        logDataToFile(result1);

        //If error; return error and not continue further commands
        if (!result1[0].equals("0")){
            return result1;
        }

        //Run cd to git directory and log the output
        result2 = runCommand(cloneDirectory, osShell[0], osShell[1], "cd", gitDirectory);
        logDataToFile(result2);

        //If error; return error and not continue further commands
        if (!result2[0].equals("0")){
            return result2;
        }

        //Run git checkout to the pushed branch and log the output
        result3 = runCommand(cloneDirectory, osShell[0], osShell[1], "git", "checkout", gitId);
        logDataToFile(result3);

        //If error; return error and not continue further commands
        if (!result3[0].equals("0")){
            return result3;
        }

        //Run ./gradlew build in current directory and se if code can build. Also logs the result
        result4 = runCommand(cloneDirectory, osShell[0], osShell[1], "./gradlew", "build");
        logDataToFile(result4);


        //Need to remove directory
        //runCommand(cloneDirectory, osShell[0], osShell[1], "rm", "-r", gitDirectory);

        return result4;

    }
}
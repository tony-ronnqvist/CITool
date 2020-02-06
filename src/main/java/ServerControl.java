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

            if (!output[0].equals("0")) {
                //Get error messages for error as string and insert in output
                output[1] = convertStreamToString(process.getErrorStream());
            } else {
                //Get input messages from console as string and insert in output
                output[1] = convertStreamToString(process.getInputStream());

            }
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
        String bytesAsString = bytes.toString();

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
                    " - Failed - Error: " + logData[1] + "\n";
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
     * operating systems Linux and Windows; if not windows assumes that it is Linux.
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
        } else {
            //Else assume operating system is Linux
            output[0] = "bash";
            output[1] = "-c";
        }
        return output;
    }

    /**
     * @param json
     * @return
     * @throws IOException
     */
    public static String[] cloneAndBuildMac(String json) throws IOException {

        //Get all required strings from JsonParser.
        String gitAddr = JsonParser.get_clone_url(json);
        String gitId = JsonParser.get_sha_pull_request(json);
        String gitDir = JsonParser.get_full_name(json);
        String firstDir = System.getProperty("user.dir");
        String tmpFolder = "tempGitCloneCI";
        String lineSep = System.getProperty("line.separator");

        //Init all steps
        String[] resultStep0; String[] resultStep1; String[] resultStep2; String[] resultStep3; String[] resultStep4;


        ////STEP 0: Get the "root" folder to be able to remove the temporary directory later
        resultStep0 = runCommand(firstDir, "cd", "..", "&&", "pwd");
        //IF ERROR: Return error and log if not successful
        if (!resultStep0[0].equals("0")) {
            logDataToFile(resultStep0);
            return resultStep0;
        }

        ////STEP 1 : Make temporary directory, change to that directory, and check and save path for future use
        resultStep1 = runCommand(resultStep0[1].replace(lineSep, ""), "mkdir", tmpFolder, "&&",
        "cd", tmpFolder, "&&", "pwd");
        //IF ERROR: Remove temporary folder and return error and do not issue further commands
        if (!resultStep1[0].equals("0")) {
            logDataToFile(resultStep1);
            runCommand(resultStep0[1].replace(lineSep, ""), "rm", "-r", tmpFolder);
            return resultStep1;
        }


        ////STEP 2: Git clone in current directory, change to cloned directory and save path for future use
        resultStep2 = runCommand(resultStep1[1].replace(lineSep, ""), "git", "clone", gitAddr,
        "&&", "cd", gitDir, "&&", "pwd");
        //IF ERROR: Remove temporary folder and return error and do not issue further commands
        if (!resultStep2[0].equals("0")) {
            logDataToFile(resultStep2);
            runCommand(resultStep0[1].replace(lineSep, ""), "rm", "-r", tmpFolder);
            return resultStep2;
        }


        ////STEP 3/4: Build with gradle and remove temporary folder
        resultStep3 = runCommand(resultStep2[1].replace(lineSep, ""),"git", "checkout", gitId,
        "&&", "./gradlew", "build");
        resultStep4 = runCommand(resultStep0[1].replace(lineSep, ""), "rm", "-r", tmpFolder);
        //IF ERROR: Log that folder was not removed
        if (!resultStep4[0].equals("0")) {
            logDataToFile(resultStep4);
            return resultStep4;
        }

        //Log as successful if all above task exited without error
        logDataToFile(resultStep3);
        return resultStep3;

    }

    /**
     * @param json
     * @return
     * @throws IOException
     */
    public static String[] cloneAndBuildWin(String json) throws IOException {
        //Get all required strings from JsonParser.
        String gitAddr = JsonParser.get_clone_url(json);
        String gitId = JsonParser.get_sha_pull_request(json);
        String gitDir = JsonParser.get_full_name(json);
        String firstDir = System.getProperty("user.dir");
        String tmpFolder = "tempGitCloneCI";
        String[] oSh = getOsShell();
        String lineSep = System.getProperty("line.separator");

        //Init all steps
        String[] resultStep0; String[] resultStep1; String[] resultStep2; String[] resultStep3; String[] resultStep4;


        ////STEP 0: Get the "root" folder to be able to remove the temporary directory later
        resultStep0 = runCommand(firstDir, oSh[0], oSh[1], "cd", "..", "&&", "cd");
        //IF ERROR: Return error and log if not successful
        if (!resultStep0[0].equals("0")) {
            logDataToFile(resultStep0);
            return resultStep0;
        }


        ////STEP 1 : Make temporary directory, change to that directory, and check and save path for future use
        resultStep1 = runCommand(resultStep0[1].replace(lineSep, ""), oSh[0], oSh[1],"mkdir",
        tmpFolder,
         "&&", "cd", tmpFolder, "&&", "cd");
        //IF ERROR: Remove temporary folder and return error and do not issue further commands
        if (!resultStep1[0].equals("0")) {
            logDataToFile(resultStep1);
            runCommand(resultStep0[1].replace(lineSep, ""), oSh[0], oSh[1], "rd", "/s", "/q",
            tmpFolder);
            return resultStep1;
        }


        ////STEP 2: Git clone in current directory, change to cloned directory and save path for future use
        resultStep2 = runCommand(resultStep1[1].replace(lineSep, ""), oSh[0], oSh[1],"git",
         "clone", gitAddr, "&&", "cd", gitDir, "&&", "cd");
        //IF ERROR: Remove temporary folder and return error and do not issue further commands
        if (!resultStep2[0].equals("0")) {
            logDataToFile(resultStep2);
            runCommand(resultStep0[1].replace(lineSep, ""), oSh[0], oSh[1], "rd", "/s", "/q",
            tmpFolder);
            return resultStep2;
        }


        ////STEP 3/4: Build with gradle and remove temporary folder
        resultStep3 = runCommand(resultStep2[1].replace(lineSep, ""),oSh[0], oSh[1], "git","-c"
        ,"advice.detachedHead=false", "checkout", gitId, "&&", "gradlew.bat");
        resultStep4 = runCommand(resultStep0[1].replace(lineSep, ""), oSh[0], oSh[1], "rd", "/s"
        , "/q", tmpFolder);
        //IF ERROR: Log that folder was not removed
        if (!resultStep4[0].equals("0")) {
            logDataToFile(resultStep4);
            return resultStep4;
        }

        //Log as successful if all above task exited without error
        logDataToFile(resultStep3);
        return resultStep3;

    }
}
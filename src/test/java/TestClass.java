import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.*;

public class TestClass {

    @Test
    public void test1() {
        System.out.println("Test 1 works");
    }

    /**
     * Test if the same content as the body returns as a string
     * We pass in Example String as byte code and set that as the body
     * We then send request to getJsonFromRequest and expect "Example String" in return
     */
    @Test
    public void testGetJsonFromRequest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        String str = "Example String";
        byte[] b = str.getBytes();
        request.setContent(b);

        assert JsonParser.getJsonFromRequest(request).equals(str) == true;

    }
    @Test
    public void testGetGitHubEventFromHeader(){

        /**
         * X-GitHub-Event should be accepted as a valid header name and return Example String
         * as a string
         */
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        request.addHeader("X-GitHub-Event","Example String");
        String str = "Example String";
        byte[] b = str.getBytes();
        request.setContent(b);

        assert JsonParser.getGitHubEventFromHeader(request).equals(str) == true : "Test1 failed";
        /**
         * Header name should not be accepted by getGitHubEventFromHeader
         */
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request.addHeader("NOT-X-GitHub-Event","Example String");
        str = "Example String";
        b = str.getBytes();
        request.setContent(b);

        assert JsonParser.getGitHubEventFromHeader(request2).equals(str) == false : "Test2 failed ";
    }

    /**
     * Input: Json with id object
     * Output: String with the id
     *
     * Given a json as string getCommitId should return a string of the id.
     */
    @Test
    public void testGetCommitId(){
        String testJson = "{\"ref\":\"refs/heads/iuaheiuaehf\",\"before\":\"fef9c941948e5d2594dbaf7929699b7cc28fa706\",\"after\":\"5e6db92f3238696f063942991483647008c5cbf1\",\"repository\":{\"id\":237486044,\"node_id\":\"MDEwOlJlcG9zaXRvcnkyMzc0ODYwNDQ=\",\"name\":\"CITEST\",\"full_name\":\"feluxz/CITEST\",\"private\":true,\"owner\":{\"name\":\"feluxz\",\"email\":\"felix_m_is@hotmail.com\",\"login\":\"feluxz\",\"id\":29494534,\"node_id\":\"MDQ6VXNlcjI5NDk0NTM0\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/29494534?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/feluxz\",\"html_url\":\"https://github.com/feluxz\",\"followers_url\":\"https://api.github.com/users/feluxz/followers\",\"following_url\":\"https://api.github.com/users/feluxz/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/feluxz/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/feluxz/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/feluxz/subscriptions\",\"organizations_url\":\"https://api.github.com/users/feluxz/orgs\",\"repos_url\":\"https://api.github.com/users/feluxz/repos\",\"events_url\":\"https://api.github.com/users/feluxz/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/feluxz/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/feluxz/CITEST\",\"description\":null,\"fork\":false,\"url\":\"https://github.com/feluxz/CITEST\",\"forks_url\":\"https://api.github.com/repos/feluxz/CITEST/forks\",\"keys_url\":\"https://api.github.com/repos/feluxz/CITEST/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/feluxz/CITEST/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/feluxz/CITEST/teams\",\"hooks_url\":\"https://api.github.com/repos/feluxz/CITEST/hooks\",\"issue_events_url\":\"https://api.github.com/repos/feluxz/CITEST/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/feluxz/CITEST/events\",\"assignees_url\":\"https://api.github.com/repos/feluxz/CITEST/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/feluxz/CITEST/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/feluxz/CITEST/tags\",\"blobs_url\":\"https://api.github.com/repos/feluxz/CITEST/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/feluxz/CITEST/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/feluxz/CITEST/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/feluxz/CITEST/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/feluxz/CITEST/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/feluxz/CITEST/languages\",\"stargazers_url\":\"https://api.github.com/repos/feluxz/CITEST/stargazers\",\"contributors_url\":\"https://api.github.com/repos/feluxz/CITEST/contributors\",\"subscribers_url\":\"https://api.github.com/repos/feluxz/CITEST/subscribers\",\"subscription_url\":\"https://api.github.com/repos/feluxz/CITEST/subscription\",\"commits_url\":\"https://api.github.com/repos/feluxz/CITEST/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/feluxz/CITEST/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/feluxz/CITEST/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/feluxz/CITEST/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/feluxz/CITEST/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/feluxz/CITEST/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/feluxz/CITEST/merges\",\"archive_url\":\"https://api.github.com/repos/feluxz/CITEST/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/feluxz/CITEST/downloads\",\"issues_url\":\"https://api.github.com/repos/feluxz/CITEST/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/feluxz/CITEST/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/feluxz/CITEST/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/feluxz/CITEST/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/feluxz/CITEST/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/feluxz/CITEST/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/feluxz/CITEST/deployments\",\"created_at\":1580493358,\"updated_at\":\"2020-01-31T17:57:49Z\",\"pushed_at\":1580747292,\"git_url\":\"git://github.com/feluxz/CITEST.git\",\"ssh_url\":\"git@github.com:feluxz/CITEST.git\",\"clone_url\":\"https://github.com/feluxz/CITEST.git\",\"svn_url\":\"https://github.com/feluxz/CITEST\",\"homepage\":null,\"size\":754,\"stargazers_count\":0,\"watchers_count\":0,\"language\":\"HTML\",\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":13,\"license\":null,\"forks\":0,\"open_issues\":13,\"watchers\":0,\"default_branch\":\"master\",\"stargazers\":0,\"master_branch\":\"master\"},\"pusher\":{\"name\":\"feluxz\",\"email\":\"felix_m_is@hotmail.com\"},\"sender\":{\"login\":\"feluxz\",\"id\":29494534,\"node_id\":\"MDQ6VXNlcjI5NDk0NTM0\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/29494534?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/feluxz\",\"html_url\":\"https://github.com/feluxz\",\"followers_url\":\"https://api.github.com/users/feluxz/followers\",\"following_url\":\"https://api.github.com/users/feluxz/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/feluxz/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/feluxz/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/feluxz/subscriptions\",\"organizations_url\":\"https://api.github.com/users/feluxz/orgs\",\"repos_url\":\"https://api.github.com/users/feluxz/repos\",\"events_url\":\"https://api.github.com/users/feluxz/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/feluxz/received_events\",\"type\":\"User\",\"site_admin\":false},\"created\":false,\"deleted\":false,\"forced\":false,\"base_ref\":null,\"compare\":\"https://github.com/feluxz/CITEST/compare/fef9c941948e...5e6db92f3238\",\"commits\":[{\"id\":\"5e6db92f3238696f063942991483647008c5cbf1\",\"tree_id\":\"b624dfdf11a90c642be2d48c0ee0ce765ea028ee\",\"distinct\":true,\"message\":\"hejhej\",\"timestamp\":\"2020-02-03T17:28:06+01:00\",\"url\":\"https://github.com/feluxz/CITEST/commit/5e6db92f3238696f063942991483647008c5cbf1\",\"author\":{\"name\":\"fmagnell\",\"email\":\"fmagnell@kth.se\"},\"committer\":{\"name\":\"fmagnell\",\"email\":\"fmagnell@kth.se\"},\"added\":[],\"removed\":[],\"modified\":[\".gradle/5.2.1/executionHistory/executionHistory.bin\",\".gradle/5.2.1/executionHistory/executionHistory.lock\",\".gradle/5.2.1/fileContent/fileContent.lock\",\".gradle/5.2.1/fileHashes/fileHashes.bin\",\".gradle/5.2.1/fileHashes/fileHashes.lock\",\".gradle/5.2.1/fileHashes/resourceHashesCache.bin\",\".gradle/5.2.1/javaCompile/classAnalysis.bin\",\".gradle/5.2.1/javaCompile/jarAnalysis.bin\",\".gradle/5.2.1/javaCompile/javaCompile.lock\",\".gradle/5.2.1/javaCompile/taskHistory.bin\",\".gradle/buildOutputCleanup/buildOutputCleanup.lock\",\".gradle/buildOutputCleanup/outputFiles.bin\",\".idea/workspace.xml\",\"build.gradle\",\"build/reports/tests/test/classes/TestClass.html\",\"build/reports/tests/test/css/base-style.css\",\"build/reports/tests/test/css/style.css\",\"build/reports/tests/test/index.html\",\"build/reports/tests/test/js/report.js\",\"build/reports/tests/test/packages/default-package.html\",\"build/test-results/test/TEST-TestClass.xml\",\"build/test-results/test/binary/output.bin\",\"build/test-results/test/binary/output.bin.idx\",\"build/test-results/test/binary/results.bin\",\"src/main/java/CIServer.java\",\"src/main/java/JsonParser.java\",\"src/test/java/TestClass.java\"]}],\"head_commit\":{\"id\":\"5e6db92f3238696f063942991483647008c5cbf1\",\"tree_id\":\"b624dfdf11a90c642be2d48c0ee0ce765ea028ee\",\"distinct\":true,\"message\":\"hejhej\",\"timestamp\":\"2020-02-03T17:28:06+01:00\",\"url\":\"https://github.com/feluxz/CITEST/commit/5e6db92f3238696f063942991483647008c5cbf1\",\"author\":{\"name\":\"fmagnell\",\"email\":\"fmagnell@kth.se\"},\"committer\":{\"name\":\"fmagnell\",\"email\":\"fmagnell@kth.se\"},\"added\":[],\"removed\":[],\"modified\":[\".gradle/5.2.1/executionHistory/executionHistory.bin\",\".gradle/5.2.1/executionHistory/executionHistory.lock\",\".gradle/5.2.1/fileContent/fileContent.lock\",\".gradle/5.2.1/fileHashes/fileHashes.bin\",\".gradle/5.2.1/fileHashes/fileHashes.lock\",\".gradle/5.2.1/fileHashes/resourceHashesCache.bin\",\".gradle/5.2.1/javaCompile/classAnalysis.bin\",\".gradle/5.2.1/javaCompile/jarAnalysis.bin\",\".gradle/5.2.1/javaCompile/javaCompile.lock\",\".gradle/5.2.1/javaCompile/taskHistory.bin\",\".gradle/buildOutputCleanup/buildOutputCleanup.lock\",\".gradle/buildOutputCleanup/outputFiles.bin\",\".idea/workspace.xml\",\"build.gradle\",\"build/reports/tests/test/classes/TestClass.html\",\"build/reports/tests/test/css/base-style.css\",\"build/reports/tests/test/css/style.css\",\"build/reports/tests/test/index.html\",\"build/reports/tests/test/js/report.js\",\"build/reports/tests/test/packages/default-package.html\",\"build/test-results/test/TEST-TestClass.xml\",\"build/test-results/test/binary/output.bin\",\"build/test-results/test/binary/output.bin.idx\",\"build/test-results/test/binary/results.bin\",\"src/main/java/CIServer.java\",\"src/main/java/JsonParser.java\",\"src/test/java/TestClass.java\"]}}\n";
        String id = "\"5e6db92f3238696f063942991483647008c5cbf1\"";
        assert JsonParser.getCommitId(testJson).equals(id) == true : "Test1 failed";
    }
    /**
     * Input: Process builder with set outputs
     * Output: Array - with exit code and error messages if any
     *
     * Given the mocked output the runCommand should return an array with exit code and a error messages if any
     */
    @Test
    public void testRunCommand(@Mocked ProcessBuilder testBuilder)
            throws IOException, InterruptedException {

        //Initialize entries for output exit code and error stream
        int expectedExitCode = 1;
        byte[] expectedErrorMessages = "Error".getBytes();

        ///Initialize input and output pipe
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream errorStream = new PipedInputStream();

        //Connect the pipes
        errorStream.connect(outputStream);

        //Generate data in input stream through output stream
        for (int i = 0; i < expectedErrorMessages.length; i++) {
            outputStream.write(expectedErrorMessages[i]);
        }

        //Close output stream
        outputStream.close();

        //Mock error stream and exit code of processBuilder
        new Expectations() {{

            testBuilder.start().getErrorStream(); result = errorStream;
            testBuilder.start().waitFor(); result = expectedExitCode;

        }};

        //Generated expected output given the mocking above
        String[] expectedOutput = {String.valueOf(expectedExitCode),"Error"};

        //Init output string array
        String[] output;

        //Run ServerControl.runCommand() and assert that the returned string array is correct
        output = ServerControl.runCommand("C:\\Users\\tony_\\Desktop",
                "Cmd.exe","/c", "mkdir", "test");

        assert output[0].equals(expectedOutput[0]) : "Test1 - Failed: First entry not equal";
        assert output[1].equals(expectedOutput[1]) : "Test2 - Failed: Second entry not equal";
    }

    /**
     * Input: An log entry in an string array, where first entry is the exit code and second the error msg.
     * Output: A log file in current directory called ServerLog.txt with the log entry on the last line
     *
     * Given the the above input the a file should be created and the last entry should be then one entered
     *
     * @throws IOException - if logfile exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    @Test
    public void testLogDataToFile() throws IOException {

        //Initialize test log entry and the expected output
        String[] testLogEntry = {"1", "Test"};
        String expectedEndOutput = "1 - Failed - Error: Test";
        String notExpectedEndOutput = "one hundred sheep";

        //Run logDataToFile method
        ServerControl.logDataToFile(testLogEntry);

        //Set path to where file was generated
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory += "/ServerLog.txt";

        //Initialize file in java
        File file = new File(currentDirectory);

        //Check if file was generated and therefore exist
        boolean fileExist = file.exists();
        assert fileExist : "Test 1 - Failed: File not created";

        //Read the file and save the last line of the file
        String line;
        String lastLineInFile = "";

        FileReader fileReader = new FileReader(file);

        BufferedReader bufferReader = new BufferedReader(fileReader);

        while ((line = bufferReader.readLine()) != null) {
            lastLineInFile = line;
        }

        //Close the fileReader and the delete file
        fileReader.close();
        file.delete();

        //Assert that the end of the end of the expected output is equal to the end of the output
        assert lastLineInFile.endsWith(expectedEndOutput) : "Test 2 - Failed: Not correct entry on last line";
        assert !lastLineInFile.endsWith(notExpectedEndOutput) : "Test 3 - Failed: Returned wrong entry";

    }

}
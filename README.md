# Continuous Integration server :

## Project description

The CI server decides whether a push or pull request should be merged or not, depending on compilation and testing.

Compilation & Testing: The CI server first perform a static syntax check for languages without compiler.Then CI server execute the automated tests of the group project. To do that, CI server first make temporary directory and git clone in current directory. Then checkout the cloned branch and compilate & test with gradle by running the command:

```
  gradlew build
```
The program is tested with JUnit as the unit-tests. 

Notification: After compilation and testing, the CI server sends notification of CI results (Commit status) back to GitHub. Commit statuses with state, target_url, description and context are created for a given SHA. Then the commit statuses with the GitHub personal access token would be sent back to GitHub through HttpPost. 

Lastly, the CI server keeps the history of the past builds by sending information to the database. Each build is given a unique URL, that is accessible to get the build information.

## Documentation :mag_right:

The documentation can be found [:link: HERE](https://sleepy-allen-9161d8.netlify.com)

The list of the past builds can be found [:link: HERE](https://citool.firebaseapp.com/builds)

## API :memo:

### CIServer

[CIServer.java](src/main/java/CIServer.java) is a continuous integration server which acts as webhook.

[TestClass.java](src/test/java/TestClass.java) contain all the unit-tests for comilation, testing and notification.

## Requirement:
Add a token.txt file which only contain your GitHub Personal access tokens under directory: CITool\ .

## Compatibility policies:

- The API of this library is frozen :snowflake:.
- Version numbers adhere to semantic versioning.

The only accepted reason to modify the API of this package
is to handle issues that can't be resolved in any other
reasonable way.

## Statement of contribution :gift_heart:

Berggren, Christina @chrpete - Databse integration in CIServer class with server and Javadoc

Leung, Jacky @ksjleung -  status notifications in CIServer class to github and readme

Lindström, Ruben @rubenli - react client app

Magnell, Felix @fmagnell - JsonParser class and CIServer class

Rönnqvist, Tony @tonyr - ServerControl class and CIServer class

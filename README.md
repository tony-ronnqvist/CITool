# Continuous Integration server :

## Project description

The CI server decides whether a push or pull request should be merged or not, depending on compilation and testing.

Compilation & Testing: The CI server first perform a static syntax check for languages without compiler.Then CI server execute the automated tests of the group project. To do that, CI server first make temporary directory and git clone in current directory. Then checkout the cloned branch and compilate & test with gradle by running the command:

```
  gradlew build
```

Notification: After compilation and testing, the CI server sends notification of CI results (Commit status) back to GitHub. Commit statuses with state, target_url, description and context are created for a given SHA. Then the commit statuses with the GitHub personal access token would be sent back to GitHub through HttpPost. 

Lastly, the CI server keeps the history of the past builds by sending information to the database. Each build is given a unique URL, that is accessible to get the build information.

## Documentation :mag_right:

The documentation can be found [:link: HERE]()

The list of the past builds can be found [:link: HERE]()

## API :memo:

### CIServer

[CIServer.java](src/main/CIServer.java) is a continuous integration server which acts as webhook.



## Compatibility policies:

- The API of this library is frozen :snowflake:.
- Version numbers adhere to semantic versioning.

The only accepted reason to modify the API of this package
is to handle issues that can't be resolved in any other
reasonable way.

## Statement of contribution :gift_heart:

Berggren, Christina @chrpete

Leung, Jacky @ksjleung

Lindström, Ruben @rubenli

Magnell, Felix @fmagnell

Rönnqvist, Tony @tonyr

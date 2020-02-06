# Continuous Integration server :

## Program desciption

This program decides whether a interceptor should be launch or not, depending on input radar info. :rocket:

More specifically, the `DECIDE` function (more on this in the API-section) checks 15 conditions called _the Launch Interceptor Conditions (LIC)_ (which are represented in separate functions - again, this is elaborated on the API-section). These conditions are fed 100 _planar data points_ (the radar echos), which returns a total of 15 boolean values, that together make up the _(conditions met vector (CMV)_.

The _Logical Connector Matrix (LCM)_ define which individual LIC are to be considered jontly in some way. This of course becomes a symmetric matrix of dimensions 15x15, with possible values **ANDD**, **ORR** or **NOTUSED**. The LCM and CMV are stored in the _Preliminary Unlocking Matrix (PUM)_, another 15x15 symmetric matrix.

Lastly, the _Preliminary Unlocking Vector (PUV)_ represents which LIC acctually matter, and indicates how to combine the PUM values to form the \__Final Unlocking Vector (FUV)_, which will consist of 15 booleans. \*_Iff_ all the values of the vector are true, the launch will be approved :boom:

## Documentation :mag_right:

The documentation can be found [:link: HERE](https://launch-docs.netlify.com/)

## API :memo:

### DECIDE

`Decide()` is a function in [LaunchInterceptorProgram](src/LaunchInterceptorProgram.java) that runs the program. It's in- and outputs are listed below.

- INPUT: int NUMPOINTS, Point[] Points, PARAMETERS a, String[15][15] LCM, boolean[15] PUV
- OUTPUT: boolean result

The 15 conditions that make out the LIC are listed below

### LIC

[LIC.java](src/main/LIC.java) contains the 15 conditions, the _LIC_. The output of every condition-function is a boolean. Therefore, only the input will be listed.

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
